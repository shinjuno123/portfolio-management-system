jQuery(document).ready(function ($) {
  // glass covering effect on project images
  (function runCoverGlassEffect() {
    window.onload = function () {
      coverGlassOnProjectImage();
    };
    $(window).resize(function () {
      // resize event throttling for performance...
      setTimeout(function () {
        coverGlassOnProjectImage();
      }, 200);
    });

    $(".left-button,.right-button").click(function () {
      coverGlassOnProjectImage();
    });

    function coverGlassOnProjectImage() {
      const glasses = document.querySelectorAll(".glass");
      glasses.forEach((glass) => {
        const projectImage = glass.nextSibling.nextSibling;
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
      noteworthyProject: false,
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
      noteworthyProject: $("#noteworthyProject"),
      noteworthyProjectArticle: $("#noteworthProjectArticle"),
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
    elements.noteworthyProject.css({ opacity: 0 });

    let options = {
      root: null,
      rootMargin: "0px",
      threshold: 1.0,
    };

    function addViewportIntersectionEventWithAnimation() {
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
      observer.observe(elements.contact.get(0));
      observer.observe(elements.noteworthyProject.get(0));
    }

    // onload
    addViewportIntersectionEventWithAnimation();

    window.addEventListener("scroll", function () {
      addViewportIntersectionEventWithAnimation();
    });
  })();

  // add clickable link to projects boxes
  (function addClickableLinkToProjectBox() {
    const projectBoxList = $(".project-box");

    projectBoxList.each((index) => {
      const projectBox = projectBoxList.get(index);
      const url = projectBox.children[0].children[0].children[0].value;
      projectBox.addEventListener("click", () => {
        window.open(url);
      });
    });
  })();

  // add popup animation when hovering on projects boxes
  (function addPopeupAnimationWhenHovering() {
    const projectBoxList = $(".project-box");

    projectBoxList.mouseenter(function (event) {
      const projectBox = event.delegateTarget;
      $(projectBox)
        .css({ top: 0, position: "relative" })
        .animate({ top: -10 }, 200);
    });

    projectBoxList.mouseover(function (event) {
      const projectBox = event.delegateTarget;
      $(projectBox).css({ top: -10, position: "relative" });
    });

    projectBoxList.mouseleave(function (event) {
      const projectBox = event.delegateTarget;
      $(projectBox).animate({ top: 0 }, 200);
    });
  })();

  // Show category's content corresponding to the category button you click
  (function categoryAnimation() {
    function showCategoryContent(article) {
      const buttons = $(`#${article} > .category-buttons > div`);

      console.log(`#${article} > .category-buttons > div`);

      function findTargetElementInTechSection(button) {
        const target = button.getAttribute("data-bs-target");
        const targetElement = $(`${target}`);

        return targetElement;
      }

      function showCategory(button) {
        // hide all the category contents
        $(`#${article} > .category`).hide();

        // show only one category corresponding to the button's target id
        const targetElement = findTargetElementInTechSection(button);
        const border = $(`#${article} > .category-border`);
        border.css({ height: "2px", backgroundColor: "white", width: "0%" });

        targetElement.slideDown("fast", function () {
          border
            .css({ height: "2px", backgroundColor: "white", width: "0%" })
            .animate({ width: "100%" }, 1000);
        });
      }

      // show first category for default
      buttons.toArray().forEach((button, index) => {
        if (index > 0) {
          const targetElement = findTargetElementInTechSection(button);
          targetElement.hide();
        } else {
          showCategory(button);
        }
      });

      buttons.click((button) => {
        showCategory(button.delegateTarget);
        buttons.removeClass(["border-bottom", "border-light"]);
        $(button.delegateTarget).addClass(["border-bottom", "border-light"]);
      });
    }
    showCategoryContent("technology-article");
    showCategoryContent("experience-article");
  })();
});
