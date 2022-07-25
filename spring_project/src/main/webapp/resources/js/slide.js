/**
 * Sliding Object
 *  interval : 반응속도 1sec = 1000
 *  slideIndex 
 */
 const interval = 2000;
 
 const projectContainer = document.querySelector('#projects'); 
 const proj = document.querySelector('#projectField');
 
 const prevBtn = document.getElementById('prev');
 const nextBtn = document.getElementById('next');
 
 let slides = document.querySelectorAll('.slide');
 let slideIndex = Math.floor(slides.length / 2);
 let lastSlideIndex = Math.floor(slides.length / 2) - 1;
 let slideId;
 const slideWidth = slides[slideIndex].clientWidth;

 proj.style.transform = 'translateX(' + (-slideWidth * slideIndex) + 'px)';

const startSlide = (isFirst) => {
	slideId = setInterval(() => {
		moveToNextSlide();
	}, interval);
}

const getSlides = () => {
	return document.querySelectorAll('.slide');
}

const moveToNextSlide = () => {
	slides = getSlides();
	console.log(slides.length + " : " + slideIndex);
	
	if(slideIndex == 0) {
		lastSlideIndex = slideIndex;
		slideIndex++;	
	}else if(slideIndex == slides.length - 1) {
		lastSlideIndex = slideIndex;
		slideIndex--;	
	}else if(lastSlideIndex < slideIndex){
		lastSlideIndex = slideIndex;
		slideIndex++;
	}else{
		lastSlideIndex = slideIndex;
		slideIndex--;
	}
	
	for(idx = 0; idx < slides.length; idx++){
		slides[idx].className = (idx == slideIndex)? "slide now":"slide"; 	
	}
	
	proj.style.transform = 'translateX(' + (-slideWidth * slideIndex) + 'px)';
	proj.style.transition = 'all 1s';	
	
}


projectContainer.addEventListener('mouseover', ()=>{
	clearInterval(slideId);
});
projectContainer.addEventListener('mouseleave', startSlide);

nextBtn.addEventListener('click', function(){
	moveToNextSlide();
});
prevBtn.addEventListener('click', function(){
	moveToNextSlide();
});

startSlide(true);