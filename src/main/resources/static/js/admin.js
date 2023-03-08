function uuidv4() {
	return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(c) {
		var r = (Math.random() * 16) | 0,
			v = c == "x" ? r : (r & 0x3) | 0x8;
		return v.toString(16);
	});

}

(function addCategoryMain() {
	function addCategory(article) {
		const addButton = $(`#${article} > .category-buttons > .add`);

		const buttonHtml = (categoryId) => `
    <div
    class="col col-md-2 py-3 category-button position-relative"
    data-bs-target="#${categoryId}"
    >
        <button style="width:fit-content;height:fit-content;" class="position-absolute event-false d-flex align-items-center btn btn-primary top-0 end-0 px-2 py-2 rounded-circle">
            <span class="material-symbols-outlined" style="font-size:1rem;">
                delete
            </span>
        </button>
        <div>
            <input type="text" class="form-control" id="floatingInput" placeholder="category">
            
        </div>
    </div>
    `;

		const categoryDetail = (categoryId) => `
    <div class="row ms-1 category" id="${categoryId}" style="display:none;">
        <ul class="d-flex flex-wrap">
            <li
                class="px-3 m-2 rounded add-item-false-event d-flex align-items-center"
                style="cursor: pointer"
            >
                <span class="material-symbols-outlined text-light my-3">
                    add_circle
                </span>
            </li>
        </ul>
    </div>
    `;

		addButton.click(function(event) {
			const button = $(event.delegateTarget);
			const articleElement = $(`#${article}`);
			const lastDetailElement = $(
				articleElement.children()[articleElement.children().length - 2]
			);
			const lastCategoryButton = $(
				$(articleElement.children()[2]).children()[
				$(articleElement.children()[2]).children().length - 1
				]
			);
			const categoryId = uuidv4();
			lastDetailElement.after(categoryDetail(categoryId));
			lastCategoryButton.before(buttonHtml(categoryId));
			showCategoryContent(article);
			addItemsInCategoryMain();
			removeCategoryMain();

			// trigger clicking created category button!
			const prevButton = $(button.siblings()[button.siblings().length - 1]);
			prevButton.trigger("click");
		});
	}
	addCategory("technology-article");
})();

// remove category button
function removeCategoryMain() {
	function removeCategory(article) {
		const categoryButtons = $(
			`#${article} > .category-buttons > .category-button > .event-false`
		);

		categoryButtons.each((index) => {
			const categoryButton = $(categoryButtons[index]);
			categoryButton.click(function() {
				const id = $(categoryButton.parent()).attr("data-bs-target");
				$(id).remove();

				categoryButton.parent().remove();
			});

			categoryButton.removeClass("event-false");
		})


	}

	removeCategory("technology-article");
}
removeCategoryMain();
// add items in technology category
function addItemsInCategoryMain() {
	function addItemsInCategory(article) {
		const categoryItems = $(
			`#${article} > .category > ul > .add-item-false-event`
		);

		const listItem = () => `
        <li
            class="position-relative px-3 py-2 m-2 border border-primary rounded d-flex align-items-center"
            style="background: #0a2647"
        >
            <button style="width:fit-content;height:fit-content;" class="position-absolute event-false d-flex align-items-center btn btn-primary top-0 end-0 px-2 py-2 rounded-circle">
                <span class="material-symbols-outlined" style="font-size:1rem;">
                delete
                </span>
            </button>
            <div>
                <input type="text" class="form-control mb-2" id="floatingInput" placeholder="Skill(English Letters)">
                <input type="text" class="form-control" placeholder="Score(1~5)">
            </div>
           
        </li>
    `;

		function categoryEvent(event) {
			const button = $(event.delegateTarget);
			const parentId = button.parent().parent().attr("id");
			button.before(listItem());
			removeItemInCategoryMain(parentId);
		}


		categoryItems.click(categoryEvent);
		categoryItems.removeClass("add-item-false-event");
	}
	addItemsInCategory("technology-article");
}

addItemsInCategoryMain();

function removeItemInCategoryMain(id) {
	function removeItemInCategory(id) {
		const item = $(`#${id} > ul > li > .event-false`);


		item.click(function(event) {
			const oneItem = $(event.delegateTarget).parent();
			oneItem.remove();
		});

		item.removeClass("event-false");
	}
	removeItemInCategory(id);
}

(function addRemoveEventToDefaultCategoryitems() {
	const categories = $("#technology-article > .category");

	categories.each((index) => {
		const category = $(categories[index]);
		removeItemInCategoryMain(category.attr("id"));
	});
})();


(function controlSocialNetworkInputPosition() {
	const inputList = $(".social-media-section > .icons > input");
	inputList.css({ left: "+=140%", bottom: "+=37%", display: "none" });
})();

(function addClickEventtoSocialNetworkInput() {
	const buttons = $(".social-media-section > .icons > button");

	buttons.on("click", (event) => {

		const input = $(event.delegateTarget).next();
		if (input.css("display") === "block") {
			input.css("display", "none");
		} else {
			input.css("display", "block");
		}
	})

})();

(function addDefailtEventtoScreen() {
	$("main,nav").on("click", () => {
		const inputList = $(".social-media-section > .icons > input");
		inputList.css({ display: "none" });
	})
})();






(function addCustomFormSubmitEvent() {
	$("#saveToFile").submit(function(event) {
		
		event.preventDefault();
		
		// Detect form tag and set up Form data
		const formTag = $("#saveToFile")[0];
		const form = new FormData(formTag);
		let categoryInfo = "";

		
		// create data type corresponding to controller's parameter datatype
		$("#technology-article > .category-buttons > .category-button").map(function(_, elem) {
			const categoryId = $(elem).attr("data-bs-target").slice(1);
			const categoryName = $($(elem).children()[1]).children()[0].value;
			
			categoryInfo += categoryName + " ";
			
			
			let skills = "";
			$(`#technology-article > #${categoryId} > ul > li > div`).map((_, elem) => {
				const firstChild = $(elem).children().first();
				const lastChild = $(elem).children().last();
				
				skills += firstChild[0].value + ":" + lastChild[0].value + ",";

			});
			
			skills = skills.slice(0, skills.length - 1);
			categoryInfo += skills + "\n";
		
		});
		
		
		// Append techs data to formData
		form.append("techs",categoryInfo);
		
		
		
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
  			jqXHR.setRequestHeader(header, token);
		});
		
		$.ajax({
			url: "/admin/main",
			data: form,
			cache: false,
			contentType:false,
			processData: false,
			method: "POST",
			type:"POST",
			success: function(data){
				window.location.replace("/admin/main");
			},
			error: function(e){
				const response = e.responseJSON;
				aler(response.message);
			}
			
			
		}
		);
		
	
		
		


	});
})();

