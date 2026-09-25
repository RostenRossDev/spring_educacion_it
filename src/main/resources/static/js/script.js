/* ============================================================
   Explora el Espacio Exterior — interactividad en JS puro
   ============================================================ */

/* ---------- Datos de los planetas ---------- */
var planets = [
  {
    id: "sol",
    nombre: "El Sol",
    img: "img/planet-sol.png",
    color: "oklch(0.82 0.16 70)",
    descripcion:
      "Una estrella de tipo G que concentra el 99,8 % de la masa del sistema solar. Su fusión nuclear ilumina y calienta cada mundo que orbita a su alrededor.",
    distancia: "0 km (centro)",
    diametro: "1 391 000 km",
    dia: "25 días",
    ano: "—",
    lunas: "0",
    temperatura: "5 500 °C (superficie)",
    orbitRadius: 0,
    size: 120,
    orbitDuration: 0,
  },
  {
    id: "tierra",
    nombre: "Tierra",
    img: "img/planet-tierra.png",
    color: "oklch(0.7 0.13 230)",
    descripcion:
      "Nuestro hogar y el único mundo conocido con vida. Un océano azul, atmósfera respirable y un campo magnético que nos protege del viento solar.",
    distancia: "149,6 millones km",
    diametro: "12 742 km",
    dia: "24 horas",
    ano: "365 días",
    lunas: "1",
    temperatura: "15 °C (promedio)",
    orbitRadius: 150,
    size: 46,
    orbitDuration: 24,
  },
  {
    id: "marte",
    nombre: "Marte",
    img: "img/planet-marte.png",
    color: "oklch(0.6 0.16 40)",
    descripcion:
      "El planeta rojo, cubierto de óxido de hierro. Alberga el volcán más alto del sistema solar y es el principal candidato para la exploración humana.",
    distancia: "227,9 millones km",
    diametro: "6 779 km",
    dia: "24,6 horas",
    ano: "687 días",
    lunas: "2",
    temperatura: "-63 °C (promedio)",
    orbitRadius: 220,
    size: 38,
    orbitDuration: 45,
  },
  {
    id: "jupiter",
    nombre: "Júpiter",
    img: "img/planet-jupiter.png",
    color: "oklch(0.72 0.1 70)",
    descripcion:
      "El gigante gaseoso más grande del sistema solar. Su Gran Mancha Roja es una tormenta que lleva siglos girando y podría tragarse a la Tierra entera.",
    distancia: "778,5 millones km",
    diametro: "139 820 km",
    dia: "9,9 horas",
    ano: "12 años",
    lunas: "95",
    temperatura: "-145 °C (nubes)",
    orbitRadius: 300,
    size: 82,
    orbitDuration: 90,
  },
  {
    id: "saturno",
    nombre: "Saturno",
    img: "img/planet-saturno.png",
    color: "oklch(0.8 0.09 90)",
    descripcion:
      "Famoso por su espectacular sistema de anillos formados por hielo y roca. Es tan poco denso que flotaría en el agua si existiera un océano suficiente.",
    distancia: "1 434 millones km",
    diametro: "116 460 km",
    dia: "10,7 horas",
    ano: "29 años",
    lunas: "146",
    temperatura: "-178 °C (nubes)",
    orbitRadius: 380,
    size: 74,
    orbitDuration: 140,
  },
  {
    id: "neptuno",
    nombre: "Neptuno",
    img: "img/planet-neptuno.png",
    color: "oklch(0.6 0.15 250)",
    descripcion:
      "El mundo más lejano y ventoso. Sus tormentas alcanzan los 2 000 km/h, las más veloces del sistema solar, en un océano de hidrógeno y metano.",
    distancia: "4 495 millones km",
    diametro: "49 244 km",
    dia: "16 horas",
    ano: "165 años",
    lunas: "14",
    temperatura: "-214 °C (nubes)",
    orbitRadius: 450,
    size: 48,
    orbitDuration: 200,
  },
];

/* ============================================================
   1. Campo de estrellas interactivo (canvas + parallax)
   ============================================================ */
(function starfield() {
  var canvas = document.getElementById("starfield");
  if (!canvas) return;
  var ctx = canvas.getContext("2d");
  var stars = [];
  var w, h, dpr;
  var mouse = { x: 0, y: 0, tx: 0, ty: 0 };
  var reduce = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

  function resize() {
    dpr = Math.min(window.devicePixelRatio || 1, 2);
    w = window.innerWidth;
    h = window.innerHeight;
    canvas.width = w * dpr;
    canvas.height = h * dpr;
    canvas.style.width = w + "px";
    canvas.style.height = h + "px";
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
    buildStars();
  }

  function buildStars() {
    var count = Math.floor((w * h) / 6000);
    count = Math.max(120, Math.min(count, 420));
    stars = [];
    for (var i = 0; i < count; i++) {
      stars.push({
        x: Math.random() * w,
        y: Math.random() * h,
        z: Math.random() * 0.8 + 0.2, // depth for parallax
        r: Math.random() * 1.3 + 0.3,
        tw: Math.random() * Math.PI * 2, // twinkle phase
        tws: Math.random() * 0.02 + 0.005, // twinkle speed
      });
    }
  }

  function draw() {
    ctx.clearRect(0, 0, w, h);
    // smooth mouse follow
    mouse.x += (mouse.tx - mouse.x) * 0.05;
    mouse.y += (mouse.ty - mouse.y) * 0.05;

    for (var i = 0; i < stars.length; i++) {
      var s = stars[i];
      s.tw += s.tws;
      var twinkle = 0.5 + Math.sin(s.tw) * 0.5;
      var px = s.x + mouse.x * s.z * 40;
      var py = s.y + mouse.y * s.z * 40;

      var alpha = twinkle * s.z;
      ctx.beginPath();
      ctx.arc(px, py, s.r * (0.6 + s.z * 0.6), 0, Math.PI * 2);
      // warm gold-ish white stars
      ctx.fillStyle = "rgba(255, 240, 210, " + alpha.toFixed(3) + ")";
      ctx.fill();

      // occasional glow on the biggest, nearest stars
      if (s.z > 0.85 && s.r > 1.2) {
        ctx.beginPath();
        ctx.arc(px, py, s.r * 2.5, 0, Math.PI * 2);
        ctx.fillStyle = "rgba(245, 200, 130, " + (alpha * 0.12).toFixed(3) + ")";
        ctx.fill();
      }
    }
    requestAnimationFrame(draw);
  }

  window.addEventListener("resize", resize);
  window.addEventListener(
    "mousemove",
    function (e) {
      mouse.tx = e.clientX / w - 0.5;
      mouse.ty = e.clientY / h - 0.5;
    },
    { passive: true }
  );

  resize();
  if (reduce) {
    // draw a single static frame
    mouse.x = mouse.y = 0;
    ctx.clearRect(0, 0, w, h);
    for (var i = 0; i < stars.length; i++) {
      var s = stars[i];
      ctx.beginPath();
      ctx.arc(s.x, s.y, s.r, 0, Math.PI * 2);
      ctx.fillStyle = "rgba(255, 240, 210, " + (s.z * 0.8).toFixed(3) + ")";
      ctx.fill();
    }
  } else {
    requestAnimationFrame(draw);
  }
})();

/* ============================================================
   2. Navegación (scroll + menú móvil)
   ============================================================ */
(function nav() {
  var nav = document.getElementById("nav");
  var toggle = document.getElementById("navToggle");
  var mobile = document.getElementById("navMobile");

  window.addEventListener(
    "scroll",
    function () {
      if (window.scrollY > 20) nav.classList.add("scrolled");
      else nav.classList.remove("scrolled");
    },
    { passive: true }
  );

  if (toggle && mobile) {
    toggle.addEventListener("click", function () {
      var open = toggle.getAttribute("aria-expanded") === "true";
      toggle.setAttribute("aria-expanded", String(!open));
      mobile.hidden = open;
    });
    // close mobile menu on link click
    var links = mobile.querySelectorAll("a");
    for (var i = 0; i < links.length; i++) {
      links[i].addEventListener("click", function () {
        toggle.setAttribute("aria-expanded", "false");
        mobile.hidden = true;
      });
    }
  }
})();

/* ============================================================
   3. Hero parallax (nebulosa + contenido)
   ============================================================ */
(function heroParallax() {
  var bg = document.getElementById("heroBg");
  var content = document.getElementById("heroContent");
  if (!bg || !content) return;
  window.addEventListener(
    "scroll",
    function () {
      var y = window.scrollY;
      bg.style.transform = "translateY(" + y * 0.35 + "px)";
      content.style.transform = "translateY(" + y * -0.15 + "px)";
      content.style.opacity = String(Math.max(0, 1 - y / 600));
    },
    { passive: true }
  );
})();

/* ============================================================
   4. Explorador de planetas
   ============================================================ */
(function planetExplorer() {
  var tabsEl = document.getElementById("planetTabs");
  var detailEl = document.getElementById("planetDetail");
  if (!tabsEl || !detailEl) return;
  var current = "tierra";

  function statCard(label, value) {
    return (
      '<div class="stat"><p class="stat-label">' +
      label +
      '</p><p class="stat-value">' +
      value +
      "</p></div>"
    );
  }

  function renderTabs() {
    tabsEl.innerHTML = "";
    planets.forEach(function (p) {
      var btn = document.createElement("button");
      btn.className = "planet-tab" + (p.id === current ? " active" : "");
      btn.setAttribute("role", "tab");
      btn.setAttribute("aria-selected", String(p.id === current));
      btn.innerHTML =
        '<span class="dot" style="background:' + p.color + '"></span>' + p.nombre;
      btn.addEventListener("click", function () {
        current = p.id;
        renderTabs();
        renderDetail();
      });
      tabsEl.appendChild(btn);
    });
  }

  function renderDetail() {
    var p = planets.filter(function (x) {
      return x.id === current;
    })[0];
    detailEl.innerHTML =
      '<div class="detail-top fade-swap">' +
      '<img class="detail-img" src="' +
      p.img +
      '" alt="' +
      p.nombre +
      '" />' +
      "<div>" +
      '<h3 class="detail-name">' +
      p.nombre +
      "</h3>" +
      '<p class="detail-desc">' +
      p.descripcion +
      "</p>" +
      "</div>" +
      "</div>" +
      '<div class="detail-grid fade-swap">' +
      statCard("Distancia al Sol", p.distancia) +
      statCard("Diámetro", p.diametro) +
      statCard("Un día", p.dia) +
      statCard("Un año", p.ano) +
      statCard("Lunas", p.lunas) +
      statCard("Temperatura", p.temperatura) +
      "</div>";
  }

  renderTabs();
  renderDetail();
})();

/* ============================================================
   5. Sistema solar (orrery animado)
   ============================================================ */
(function solarSystem() {
  var el = document.getElementById("orrery");
  var caption = document.getElementById("orreryCaption");
  if (!el) return;

  var maxOrbit = 450; // Neptuno
  var box = 640; // matches CSS max size
  var scale = (box / 2 - 60) / maxOrbit;
  var selected = null;

  var sun = planets[0];
  var orbiting = planets.slice(1);

  // Sun glow + node
  var glow = document.createElement("div");
  glow.className = "sun-glow";
  el.appendChild(glow);

  var sunBtn = document.createElement("button");
  sunBtn.className = "sun-node planet-node";
  sunBtn.setAttribute("aria-label", sun.nombre);
  sunBtn.innerHTML = '<img src="' + sun.img + '" alt="' + sun.nombre + '" />';
  sunBtn.addEventListener("click", function () {
    select(sun);
  });
  el.appendChild(sunBtn);

  orbiting.forEach(function (p, i) {
    var radius = p.orbitRadius * scale;
    var diameter = radius * 2;

    // static orbit ring
    var ring = document.createElement("div");
    ring.className = "orbit";
    ring.style.width = diameter + "px";
    ring.style.height = diameter + "px";
    el.appendChild(ring);

    // rotating spinner anchored at center
    var spinner = document.createElement("div");
    spinner.className = "orbit-spinner";
    spinner.style.animationDuration = p.orbitDuration + "s";
    // stagger starting angle so planets don't line up
    spinner.style.transform = "rotate(" + (i * 55) + "deg)";

    var node = document.createElement("button");
    node.className = "planet-node";
    node.setAttribute("aria-label", p.nombre);
    node.style.left = radius + "px";
    node.style.top = "0px";
    var size = Math.max(28, p.size * scale * 1.6);
    node.innerHTML =
      '<img src="' + p.img + '" alt="' + p.nombre + '" style="width:' + size + "px;height:" + size + 'px" />';
    node.addEventListener("click", function (e) {
      e.stopPropagation();
      select(p, node);
    });

    spinner.appendChild(node);
    el.appendChild(spinner);
  });

  function select(p, node) {
    selected = p.id;
    var nodes = el.querySelectorAll(".planet-node");
    for (var i = 0; i < nodes.length; i++) nodes[i].classList.remove("selected");
    if (node) node.classList.add("selected");
    if (p.id === "sol") sunBtn.classList.add("selected");
    caption.innerHTML =
      "<strong>" + p.nombre + "</strong> — " + p.temperatura + " · " + p.lunas + " luna(s)";
  }

  // pause on hover
  el.addEventListener("mouseenter", function () {
    el.classList.add("paused");
  });
  el.addEventListener("mouseleave", function () {
    el.classList.remove("paused");
  });
})();

/* ============================================================
   6. Datos cósmicos (contadores animados al hacer scroll)
   ============================================================ */
(function cosmicFacts() {
  var wrap = document.getElementById("facts");
  if (!wrap) return;
  var facts = [
    { value: 300, suffix: " mil millones", decimals: 0, label: "Estrellas", sub: "solo en la Vía Láctea" },
    { value: 13.8, suffix: " mil millones", decimals: 1, label: "Años", sub: "de edad del universo" },
    { value: 299792, suffix: " km/s", decimals: 0, label: "Velocidad de la luz", sub: "el límite cósmico" },
    { value: 8, suffix: " minutos", decimals: 0, label: "Luz del Sol", sub: "en llegar a la Tierra" },
  ];

  facts.forEach(function (f) {
    var card = document.createElement("div");
    card.className = "fact reveal";
    card.innerHTML =
      '<p class="fact-value"><span class="num">0</span><span class="suffix">' +
      f.suffix +
      '</span></p>' +
      '<p class="fact-label">' +
      f.label +
      "</p>" +
      '<p class="fact-sub">' +
      f.sub +
      "</p>";
    wrap.appendChild(card);
    f._el = card.querySelector(".num");
  });

  function countUp(el, target, decimals) {
    var duration = 1600;
    var t0 = performance.now();
    function tick(now) {
      var p = Math.min((now - t0) / duration, 1);
      var eased = 1 - Math.pow(1 - p, 3);
      var v = target * eased;
      el.textContent =
        decimals > 0 ? v.toFixed(decimals) : Math.round(v).toLocaleString("es-ES");
      if (p < 1) requestAnimationFrame(tick);
    }
    requestAnimationFrame(tick);
  }

  var started = false;
  var obs = new IntersectionObserver(
    function (entries) {
      if (entries[0].isIntersecting && !started) {
        started = true;
        facts.forEach(function (f) {
          countUp(f._el, f.value, f.decimals);
        });
        obs.disconnect();
      }
    },
    { threshold: 0.3 }
  );
  obs.observe(wrap);
})();

/* ============================================================
   7. Galería circular giratoria
   ============================================================ */
(function gallery() {
  var ring = document.getElementById("galleryRing");
  var gallery = document.getElementById("gallery");
  var label = document.getElementById("galleryLabel");
  if (!ring) return;

  var items = [
    { img: "img/galaxy.png", label: "Galaxia espiral" },
    { img: "img/planet-sol.png", label: "El Sol" },
    { img: "img/planet-tierra.png", label: "Tierra" },
    { img: "img/planet-marte.png", label: "Marte" },
    { img: "img/nebula.png", label: "Nebulosa" },
    { img: "img/planet-jupiter.png", label: "Júpiter" },
    { img: "img/planet-saturno.png", label: "Saturno" },
    { img: "img/planet-neptuno.png", label: "Neptuno" },
  ];
  var RADIUS = 300;

  items.forEach(function (item, i) {
    var angle = (i / items.length) * Math.PI * 2;
    var x = Math.cos(angle) * RADIUS;
    var y = Math.sin(angle) * RADIUS;

    var pos = document.createElement("div");
    pos.className = "gallery-pos";
    pos.style.transform =
      "translate(-50%, -50%) translate(" + x + "px, " + y + "px)";

    var tile = document.createElement("button");
    tile.className = "gallery-tile";
    tile.setAttribute("aria-label", item.label);
    tile.innerHTML =
      '<span class="frame"><img src="' +
      item.img +
      '" alt="' +
      item.label +
      '" width="112" height="112" /></span>';

    function setActive() {
      label.textContent = item.label;
    }
    tile.addEventListener("mouseenter", setActive);
    tile.addEventListener("focus", setActive);

    pos.appendChild(tile);
    ring.appendChild(pos);
  });

  gallery.addEventListener("mouseenter", function () {
    gallery.classList.add("paused");
  });
  gallery.addEventListener("mouseleave", function () {
    gallery.classList.remove("paused");
  });
})();

/* ============================================================
   8. Reveal on scroll (todas las .reveal)
   ============================================================ */
(function revealOnScroll() {
  var els = document.querySelectorAll(".reveal");
  var obs = new IntersectionObserver(
    function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add("is-visible");
          obs.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.15 }
  );
  els.forEach(function (el) {
    obs.observe(el);
  });
})();

/* ---------- Año del footer ---------- */
document.getElementById("year").textContent = new Date().getFullYear();
