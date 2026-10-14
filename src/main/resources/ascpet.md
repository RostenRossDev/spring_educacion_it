Pointcut	                                                       Significado
==================================================================================================
execution(* *(@NotVoid (*)))	                   ||     Exactamente 1 parámetro, anotado
execution(* *(@NotVoid (*), ..))	               ||     El 1º anotado, y puede haber más después
execution(* *(*, @NotVoid (*)))	                   ||     Exactamente 2 parámetros, el 2º anotado
execution(* *(*, @NotVoid (*), ..))	               ||     2º anotado, y puede haber más después
execution(* *(.., @NotVoid (*)))	               ||     El último anotado (al menos 1 parámetro)
execution(* *(.., @NotVoid (*), ..))	           ||     Cualquier posición, al menos uno anotado
execution(* *(@NotVoid (*), .., @NotVoid (*)))	   ||     1º y último anotados, cualquiera en medio
execution(* *(String, @NotVoid (Persona)))	       ||     1º String, 2º Persona anotado
====================================================================================================

Los comodines que puedes usar en el patrón de parámetros:

Símbolo	Significado
*	Un parámetro de cualquier tipo
     ..	Cero o más parámetros de cualquier tipo
     @Anotacion (*)	Un parámetro de cualquier tipo anotado con @Anotacion
     @Anotacion (String)	Un parámetro de tipo String anotado con @Anotacion
     java.lang.String	Un parámetro de tipo String exacto
     .., X, ..	X en cualquier posición (con parámetros antes o después)
     🎯 1. Cualquier parámetro anotado, en cualquier posición
     java
     @Around("execution(* *(.., @NotVoid (*), ..))")
     ✅ metodo(@NotVoid String a)

✅ metodo(String a, @NotVoid String b)

✅ metodo(@NotVoid String a, String b, @NotVoid String c)

✅ metodo(int x, @NotVoid Persona p, long y)

🎯 2. Solo el PRIMER parámetro, anotado
java
@Around("execution(* *(@NotVoid (*), ..))")
✅ metodo(@NotVoid String a)

✅ metodo(@NotVoid String a, String b, int c)

❌ metodo(String a, @NotVoid String b) — la anotación está en el segundo

❌ metodo(@NotVoid String a, @NotVoid String b) — solo matchea si el primero lo está; el resto es libre por ..

🎯 3. Solo el SEGUNDO parámetro, anotado
java
@Around("execution(* *(*, @NotVoid (*), ..))")
❌ metodo(@NotVoid String a)

✅ metodo(String a, @NotVoid String b)

✅ metodo(String a, @NotVoid String b, int c)

❌ metodo(String a, String b, @NotVoid String c)

Nota: el primer * ocupa la primera posición sin importar el tipo, y el segundo patrón exige que la segunda esté anotada.

🎯 4. Solo el ÚLTIMO parámetro, anotado
java
@Around("execution(* *(.., @NotVoid (*)))")
✅ metodo(@NotVoid String a)

✅ metodo(String a, @NotVoid String b)

✅ metodo(String a, int b, @NotVoid Persona p)

❌ metodo(@NotVoid String a, String b) — el anotado no es el último

🎯 5. Exactamente DOS parámetros, el segundo anotado
java
@Around("execution(* *(*, @NotVoid (*)))")
Ojo: aquí no hay .., así que la firma debe ser exactamente de dos parámetros.

✅ metodo(String a, @NotVoid String b)

❌ metodo(@NotVoid String a)

❌ metodo(String a, @NotVoid String b, int c)

🎯 6. El parámetro anotado debe ser de tipo específico
Si quieres que el parámetro anotado sea, por ejemplo, String:

java
@Around("execution(* *(.., @NotVoid (String), ..))")
✅ metodo(@NotVoid String a)

✅ metodo(int x, @NotVoid String b)

❌ metodo(@NotVoid Persona p) — tipo distinto

❌ metodo(@NotVoid Integer a) — tipo distinto

Con nombre totalmente cualificado para evitar ambigüedades:

java
@Around("execution(* *(.., @NotVoid (java.lang.String), ..))")
🎯 7. Combinar tipo + posición
Si quieres "el primer parámetro, que debe ser String y estar anotado":

java
@Around("execution(* *(@NotVoid (String), ..))")
✅ metodo(@NotVoid String a)

✅ metodo(@NotVoid String a, int b)

❌ metodo(@NotVoid Persona p)

❌ metodo(String a, @NotVoid String b)

🎯 8. Varios parámetros anotados obligatorios
Si el método debe tener al menos dos parámetros anotados:

java
@Around("execution(* *(@NotVoid (*), .., @NotVoid (*)))")
✅ metodo(@NotVoid String a, @NotVoid String b)

✅ metodo(@NotVoid String a, int x, @NotVoid String b)

❌ metodo(@NotVoid String a, int x) — solo uno anotado

❌ metodo(@NotVoid String a, @NotVoid String b, @NotVoid String c) — el último anotado está bien, pero necesitas al menos dos; el patrón matchea aquí también porque el primero y el último están anotados.

Esto es útil si quieres garantizar que la validación solo se aplique cuando hay al menos dos parámetros a validar.

🎯 9. Restringir el tipo de retorno
Hasta ahora usábamos * como retorno. Puedes restringirlo:

java
// Métodos que devuelven Persona
@Around("execution(Persona *(.., @NotVoid (*), ..))")

// Métodos que devuelven cualquier cosa en el paquete service
@Around("execution(* com.miempresa.service..*(.., @NotVoid (*), ..))")

// Métodos que devuelven void
@Around("execution(void *(.., @NotVoid (*), ..))")
El .. entre paquete y método significa "y todos sus subpaquetes".

🎯 10. Restringir el método por nombre o patrón
java
// Solo el método "update" con algún parámetro anotado
@Around("execution(* update(.., @NotVoid (*), ..))")

// Métodos que empiezan con "save"
@Around("execution(* save*(.., @NotVoid (*), ..))")

// Cualquier método de la clase PersonaService
@Around("execution(* com.miempresa.PersonaService.*(.., @NotVoid (*), ..))")
🎯 11. Combinar anotación de método + anotación de parámetro
Si quieres que el método esté anotado con @Auditar y algún parámetro con @NotVoid:

java
@Around("execution(* *(.., @NotVoid (*), ..)) && @annotation(com.miempresa.Auditar)")
✅ @Auditar public void update(@NotVoid String a)

❌ public void update(@NotVoid String a) — falta @Auditar

❌ @Auditar public void update(String a) — falta @NotVoid

Esta combinación es muy común: validación genérica + comportamiento específico.

🎯 12. Diferentes tipos para diferentes parámetros
Puedes exigir que un parámetro anotado sea String y otro también anotado sea Persona:

java
@Around("execution(* *(@NotVoid (String), .., @NotVoid (Persona)))")
✅ metodo(@NotVoid String nombre, @NotVoid Persona p)

✅ metodo(@NotVoid String nombre, int x, @NotVoid Persona p)

❌ metodo(@NotVoid Persona p, @NotVoid String nombre) — el primero debe ser String