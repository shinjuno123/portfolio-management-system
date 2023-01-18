function coverGlassOnProjectImage() {
  const glasses = document.querySelectorAll(".glass");
  const projectImage = document.querySelector(".project-image");
  glasses.forEach((glass) => {
    glass.style.width = projectImage.clientWidth + "px";
    glass.style.height = projectImage.clientHeight + "px";
  });

  glasses.forEach((glass) => {
    const address = glass.childNodes[1].value;
    glass.addEventListener("click", () => {
      window.open(address);
    });
  });
}

window.onload = function () {
  coverGlassOnProjectImage();
};

window.addEventListener("resize", () => {
  coverGlassOnProjectImage();
});
