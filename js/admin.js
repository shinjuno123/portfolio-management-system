(function addCategoryMain() {
  function addCategory(article) {
    const addButton = $(`#${article} > .category-buttons > .add`);

    const buttonHtml = (categoryId, categoryName) => `
    <div
    class="col col-md-2 py-3 category-button"
    data-bs-target="#${categoryId}"
    >
        <div>${categoryName}</div>
    </div>
    `;

    const categoryDetail = (categoryId) => `
    <div class="row ms-1 category" id="${categoryId}" style="display:none;">
        <ul class="d-flex flex-wrap">

        </ul>
    </div>
    `;

    addButton.click(function () {
      const technologyArticle = $(`#${article}`);
      const lastDetailElement = $(
        technologyArticle.children()[technologyArticle.children().length - 2]
      );
      const lastCategoryButton = $(
        $(technologyArticle.children()[2]).children()[
          $(technologyArticle.children()[2]).children().length - 2
        ]
      );
      lastDetailElement.after(categoryDetail("new-category"));
      lastCategoryButton.after(buttonHtml("new-category", "New Category"));
      showCategoryContent(article);
    });
  }
  addCategory("technology-article");
  addCategory("experience-article");
})();
