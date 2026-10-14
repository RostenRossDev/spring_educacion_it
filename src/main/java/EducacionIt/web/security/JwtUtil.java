package EducacionIt.web.security;

import EducacionIt.web.entities.Role;
import EducacionIt.web.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Log4j2
@Component
public class JwtUtil {

    /**
     * Clave secreta para firmar los tokens JWT.
     * Se carga desde application.properties con la propiedad ${secret.apiKey}
     * IMPORTANTE: Debe tener mínimo 256 bits (32 caracteres) para HS256
     */
    @Value("${secret.apiKey}")
    private String SECRET_KEY;


    /**
     * Tiempo de validez del token de acceso: 1 hora (3600000 ms)
     */
    private static final long ACCESS_TOKEN_VALIDITY = 3600000;

    /**
     * Tiempo de validez del token de refresco: ~1.3 horas (4600000 ms)
     */
    private static final long REFRESH_TOKEN_VALIDITY = 4600000;

    /**
     * Genera una clave criptográfica SecretKey a partir de la clave secreta.
     * Convierte el String SECRET_KEY en bytes y crea una clave HMAC-SHA256.
     *
     * @return SecretKey para firmar y verificar tokens
     */
    private SecretKey getSigningSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token Token JWT del cual extraer el username
     * @return Username almacenado en el claim "subject" del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token Token JWT del cual extraer la fecha de expiración
     * @return Fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Método genérico para extraer cualquier claim del token JWT.
     * Utiliza un Function para especificar qué claim extraer.
     *
     * Ejemplo de uso:
     * - extractClaim(token, Claims::getSubject) -> obtiene el username
     * - extractClaim(token, Claims::getExpiration) -> obtiene la fecha de expiración
     *
     * @param <T> Tipo de dato del claim a extraer
     * @param token Token JWT a procesar
     * @param claimsResolver Función que especifica qué claim extraer
     * @return Valor del claim solicitado
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        try {
            // Parse normal (valida expiración)
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            // ⚠️ Token expirado, pero devolvemos igualmente los claims
            Claims claims = ex.getClaims();
            return claimsResolver.apply(claims);
        }
    }

    /**
     * Extrae TODOS los claims (datos) contenidos en el token JWT.
     *
     * Proceso:
     * 1. Parsea el token
     * 2. Verifica la firma con la clave secreta
     * 3. Extrae el payload (carga útil) que contiene todos los claims
     *
     * @param token Token JWT a decodificar
     * @return Claims objeto que contiene toda la información del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningSecretKey())  // Verifica que la firma sea válida
                .build()
                .parseSignedClaims(token)           // Parsea el token firmado
                .getPayload();                      // Obtiene el contenido (claims)
    }

    /**
     * Verifica si el token ha expirado.
     * Compara la fecha de expiración del token con la fecha actual.
     *
     * @param token Token JWT a verificar
     * @return true si el token está expirado, false si aún es válido
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un token de acceso JWT con información adicional.
     *
     * @param userDetails Información del usuario autenticado
     * @param extraClaims Claims adicionales a incluir en el token (roles, permisos, etc.)
     * @return Token JWT firmado como String
     */
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        return createToken(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    /**
     * Genera un token de refresco (refresh token).
     * No incluye claims adicionales, solo el username y la fecha de expiración.
     *
     * @param userDetails Información del usuario autenticado
     * @return Refresh token JWT firmado como String
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), REFRESH_TOKEN_VALIDITY);
    }


    /**
     * Método privado que construye el token JWT.
     *
     * Estructura del token:
     * - Claims: Información personalizada (roles, permisos, etc.)
     * - Subject: Username del usuario
     * - IssuedAt: Fecha de creación del token
     * - Expiration: Fecha de expiración del token
     * - Firma: Firma digital con la clave secreta usando HS256
     *
     * @param claims Mapa con información adicional a incluir en el token
     * @param subject Username del usuario (claim principal)
     * @param validity Tiempo de validez del token en milisegundos
     * @return Token JWT completo y firmado
     */
    private String createToken(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .claims(claims)                                                      // Agrega claims personalizados
                .subject(subject)                                                    // Username del usuario
                .issuedAt(new Date(System.currentTimeMillis()))                     // Fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + validity))        // Fecha de expiración
                .signWith(getSigningSecretKey())                                    // Firma con algoritmo HS256
                .compact();                                                          // Genera el token final
    }


    /**
     * Valida que el token JWT sea válido y pertenezca al usuario especificado.
     *
     * Verificaciones:
     * 1. El username del token coincide con el username del UserDetails
     * 2. El token no ha expirado
     *
     * @param token Token JWT a validar
     * @param userDetails Datos del usuario para comparar
     * @return true si el token es válido, false en caso contrario
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public static String generarTokenParaUsuario(String email, @Autowired IUserService userService) {

        User userDetails = userService.getByEmail(email);
        log.debug("############################################ "+userDetails);
        Map<String, Object> additionalInfo = additionalInfo(userDetails);
        return generateTokenStatic(userDetails, additionalInfo); // O el método que uses
    }

    private static SecretKey getSigningSecretKeyStatic() {
        return Keys.hmacShaKeyFor("digitalers.clave.super.secreta.123456789".getBytes());
    }

    private static String createTokenStatic(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .claims(claims)                                                      // Agrega claims personalizados
                .subject(subject)                                                    // Username del usuario
                .issuedAt(new Date(System.currentTimeMillis()))                     // Fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + validity))        // Fecha de expiración
                .signWith(getSigningSecretKeyStatic())                                    // Firma con algoritmo HS256
                .compact();                                                          // Genera el token final
    }

    private static String generateTokenStatic(UserDetails userDetails, Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        return createTokenStatic(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    private static Map<String, Object> additionalInfo(User userDetails){
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("email", userDetails.getEmail());
        additionalInfo.put("name", userDetails.getName());
        additionalInfo.put("lastname", userDetails.getLastname());

        // Roles
        additionalInfo.put("roles", userDetails.getRoles());
        return additionalInfo;
    }
}