const carrossel = document.querySelector('.carrossel');
const slides = document.querySelectorAll('.slide');
const pontos = document.querySelectorAll('.ponto');
const prevBtn = document.querySelector('.prev-btn');
const nextBtn = document.querySelector('.next-btn');

let slideAtual = 0;
const totalSlides = slides.length;

function mostrarSlide(n) {
    slideAtual = n;
    if (slideAtual >= totalSlides) slideAtual = 0;
    if (slideAtual < 0) slideAtual = totalSlides - 1;
    
    carrossel.style.transform = `translateX(-${slideAtual * 100}%)`;
    
    pontos.forEach(ponto => ponto.classList.remove('ativo'));
    pontos[slideAtual].classList.add('ativo');
}

function proximoSlide() {
    mostrarSlide(slideAtual + 1);
}

function slideAnterior() {
    mostrarSlide(slideAtual - 1);
}

// Eventos dos botões
prevBtn.addEventListener('click', slideAnterior);
nextBtn.addEventListener('click', proximoSlide);

// Eventos dos pontos
pontos.forEach(ponto => {
    ponto.addEventListener('click', () => {
        const slideNum = parseInt(ponto.getAttribute('data-slide'));
        mostrarSlide(slideNum);
    });
});

// Troca automática a cada 5 segundos
setInterval(proximoSlide, 5000);

// Inicializar
mostrarSlide(0);