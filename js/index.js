jQuery(document).ready(function ($) {
  // glass covering effect on project images
  (function runCoverGlassEffect() {
    window.onload = function () {
      coverGlassOnProjectImage();
    };
    $(window).resize(function () {
      // resize event throttling for performance...
      setTimeout(function () {
        console.log("resize event!");
        coverGlassOnProjectImage();
      }, 200);
    });
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
  })();

  // add overal elements fade in and out effects
  (function addElementsFadeInEfftect() {
    const elementsPass = {
      introduction: false,
      aboutme: false,
      technology: false,
      experience: false,
      work: false,
      contact: false,
    };

    const elements = {
      navItems: $(".nav-item"),
      introductionArticle: $("#introduction-article"),
      introduction: $("#introduction"),
      aboutmeArticle: $("#aboutme-article"),
      aboutme: $("#aboutme"),
      technologyArticle: $("#technology-article"),
      technology: $("#technology"),
      experienceArticle: $("#experience-article"),
      experience: $("#experience"),
      workArticle: $("#work-article"),
      work: $("#work"),
      contactArticle: $("#contact-article"),
      contact: $("#contact"),
    };

    // nav items default animation
    elements.navItems
      .css({ top: -10, opacity: 0, position: "relative" })
      .animate({ top: 0, opacity: 1 }, 500);

    // introduction default animation
    elements.introductionArticle
      .css({ top: 50, position: "relative" })
      .animate({ top: 0 }, 500);

    elements.introduction.css({ opacity: 0 }, 20).animate({ opacity: 1 }, 1000);

    // default settings for other sections
    elements.aboutme.css({ opacity: 0 });
    elements.technology.css({ opacity: 0 });
    elements.experience.css({ opacity: 0 });
    elements.work.css({ opacity: 0 });
    elements.contact.css({ opacity: 0 });

    let options = {
      root: null,
      rootMargin: "0px",
      threshold: 1.0,
    };

    window.addEventListener("scroll", function () {
      let callback = (entries, observer) => {
        entries.forEach((entry) => {
          const intersectionRatio = entry.intersectionRatio;

          // when intersectionRatio gets over 60 % visibility,
          // make the element from fade out to fade in
          // once the element has been fade out and in, the element doesn't do it again.
          if (intersectionRatio > 0.6 && !elementsPass[entry.target.id]) {
            // fade out and in
            elementsPass[entry.target.id] = true;
            elements[entry.target.id + "Article"]
              .css({ top: 50, position: "relative" })
              .animate({ top: 0 }, 500);
            elements[entry.target.id].animate({ opacity: 1 }, 1000);
          }
        });
      };

      let observer = new IntersectionObserver(callback, options);
      observer.observe(elements.introduction.get(0));
      observer.observe(elements.aboutme.get(0));
      observer.observe(elements.technology.get(0));
      observer.observe(elements.experience.get(0));
      observer.observe(elements.work.get(0));
    });
  })();
});
