const projectInfo = (isActive, number, id = null, projectTitle = "", projectDescription = "",
	projectUrl = "", projectImageUrl = "") => `
<div th:fragment="projectInfo"
				data-bs-number="${number}"
				db-id="${id}"
				class="carousel-item ${isActive} project-info border border-3 border-primary rounded text-dark">


				<div class="image py-3">
					<div class="text-center">
						<img class="rounded img-thumbnail"
							src="${projectImageUrl}">
						<input type="file">
					</div>
				</div>
				<div class="container description text-center py-3">
					<div class="form-floating pb-3">
						<input type="text" class="form-control"
							placeholder="Project Title" id="floatingTitleArea" value="${projectTitle}"> <label
							for="floatingTitleArea">Project Title</label>
					</div>
					<div class="form-floating pb-3">
						<textarea class="form-control" rows="10"
							placeholder="Project Description" id="floatingDescriptionArea">
							${projectDescription}
							</textarea>
						<label for="floatingDescriptionArea">Project Description</label>
					</div>

					<div class="form-floating pb-3">
						<input class="form-control" type="url" placeholder="Project URL"
							id="floatingDescriptionArea" value="${projectUrl}"> <label
							for="floatingDescriptionArea">Project URL</label>
					</div>

					<!-- Delete Button -->
					<button type="button"
						class="w-100 align-items-center btn btn-danger px-2 py-2">
						<span class="material-symbols-outlined" style="font-size: 1rem;">
						delete
						</span>
						 <span> Delete</span>
					</button>
				</div>
			</div>
`

const projectIndicatorButton = (slideNumber, isActive) => `
 <button type="button" data-bs-target="#projectslide" data-bs-slide-to="${slideNumber}" class="${isActive}" aria-label="Slide 1"></button>
`





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
	inputList.css({ left: "+=140%", bottom: "+=37%", display: "block", visibility: "hidden" });
})();

(function addClickEventtoSocialNetworkInput() {
	const buttons = $(".social-media-section > .icons > button");

	buttons.on("click", (event) => {

		const input = $(event.delegateTarget).next();
		if (input.css("visibility") === "visible") {
			input.css("visibility", "hidden");
		} else {
			input.css("visibility", "visible");
		}
	})


})();

(function addDefailtEventtoScreen() {
	$("main,nav").on("click", () => {
		const inputList = $(".social-media-section > .icons > input");
		inputList.css({ visibility: "hidden" });
	})
})();


(function displayImageWhenUploadingFaceImage() {
	const facePhotoInputTag = $(".my-face");
	const imgTag = $(".my-face").siblings("img");

	imgTag.on("click", function() {
		facePhotoInputTag.trigger("click");
	});

	facePhotoInputTag.on("change", function(event) {
		const facePhotoFiles = facePhotoInputTag.prop('files');
		const fileReader = new FileReader();
		fileReader.onload = function() {
			imgTag[0].src = fileReader.result;
		}

		fileReader.readAsDataURL(facePhotoFiles[0]);
	});


})();






(async function loadProjectInfo() {
	const token = $("meta[name='_csrf']").attr("content");
	const tokenKey = $("meta[name='_csrf_header']").attr("content");

	// initialize header
	const neededHeader = {};

	neededHeader[tokenKey] = token;

	const projects = await fetch('/admin/work', {
		method: 'GET',
		headers: neededHeader
	}).then(response => response.json());


	for (let project of projects) {
		addProjectInfo(project.id, project.projectTitle, project.projectDescription,
			project.projectUrl, project.projectImageUrl)
	}
	changeInputValue();
	saveProjects();
})()


function addDeleteProjectInfoEvent(carouselItem) {
	carouselItem.children(".description").children("button")
		.on("click", function() {
			// get number of items after deleting selected slide
			const currentItemLength = carouselItem.siblings().length;
			const deletedItemNumber = carouselItem.attr("data-bs-number");

			// delete selected slide
			$(carouselItem).remove();

			// delete a slide button having selected slide number
			$(`#work-article > #projectslide > .carousel-indicators > button[data-bs-slide-to="${deletedItemNumber}"]`).remove();

			// get a slide number to activate in carousel
			let nextActivatedItemNumber = deletedItemNumber - 1;

			if (nextActivatedItemNumber < 0) {
				nextActivatedItemNumber += currentItemLength;
			}

			// initialize all slides and buttons from 0 to currentItemLength - 1
			const projectSlides = $("#work-article > #projectslide > .carousel-inner > .carousel-item");
			const projectSlideButtons = $("#work-article > #projectslide > .carousel-indicators > button");

			let newSlideNumber = 0;

			projectSlides.each((slideNumber) => {
				const projectSlide = $(projectSlides.get(slideNumber));
				projectSlide.attr("data-bs-number", `${newSlideNumber}`);
				newSlideNumber += 1;
			})

			newSlideNumber = 0;

			projectSlideButtons.each((slideButtonNumber) => {
				const projectSlideButton = $(projectSlideButtons.get(slideButtonNumber));
				projectSlideButton.attr("data-bs-slide-to", `${newSlideNumber}`);
				newSlideNumber += 1;
			})

			// activate nextActivated slide and the button
			const projectSlideToActivate = $(`#work-article > #projectslide > .carousel-inner > .carousel-item[data-bs-number=${nextActivatedItemNumber}]`);
			const projectSlideButtonToActivate = $(`#work-article > #projectslide > .carousel-indicators > button[data-bs-slide-to=${nextActivatedItemNumber}]`);

			projectSlideToActivate.addClass("active");
			projectSlideButtonToActivate.addClass("active");

		})
};



function addProjectInfo(id = null, projectTitle = "", projectDescription = "",
	projectUrl = "", projectImageUrl = "") {
	const projectitemsLength = $("#work-article > #projectslide > .carousel-inner > .carousel-item").length;
	const projectSlides = $("#work-article > #projectslide > .carousel-inner");
	const projectSlideButtons = $("#work-article > #projectslide > .carousel-indicators");
	let activeStatus = "";


	if (projectitemsLength === 0) {
		activeStatus = "active";
	}

	const newProject = projectInfo(activeStatus, projectitemsLength,id, projectTitle, projectDescription,
	projectUrl, projectImageUrl);
	const indicator = projectIndicatorButton(projectitemsLength, activeStatus);

	$(newProject).appendTo(projectSlides);
	$(indicator).appendTo(projectSlideButtons);
	
	const justAddedCarouselItem = $(`#work-article > #projectslide > .carousel-inner > div[db-id="${id}"]`)
	
	addDeleteProjectInfoEvent(justAddedCarouselItem);
	uploadProjectImage(justAddedCarouselItem);
}

(function addProjectinfoEventToButton() {
	$("#work-article > h3 > button").on("click", () => {

		const projectitemsLength = $("#work-article > #projectslide > .carousel-inner > .carousel-item").length;
		addProjectInfo();

		const justAddedCarouselItem = $(`#work-article > #projectslide > .carousel-inner > div[data-bs-number="${projectitemsLength}"]`)

		addDeleteProjectInfoEvent(justAddedCarouselItem);
		uploadProjectImage(justAddedCarouselItem);

	});
})();



function uploadProjectImage(carouselItem) {
	// Click project image tag then trigger click to input tag
	const projectSlideImages = carouselItem.children(".image").children("div").children("img");
	const projectInputTags = carouselItem.children(".image").children("div").children("input");

	projectSlideImages.on("click", (event) => {
		const inputTag = $(event.currentTarget).next();
		inputTag.trigger("click");
	})


	// when the file input tag is uploaded,
	projectInputTags.on("change", (event) => {
		// Change the picture of image tag to the uploaded file
		const imgTag = $(event.currentTarget).prev();
		const inputTag = $(event.currentTarget);

		const image = inputTag.prop("files");
		const fileReader = new FileReader();

		fileReader.onload = function() {
			imgTag.attr("src", fileReader.result);
		}

		fileReader.readAsDataURL(image[0]);
	})



};



function changeInputValue(){
	$("input").on("input", function(event){
		$(event.currentTarget).attr("val",event.currentTarget.value);
	});
};


/*
id = null, projectTitle = "", projectDescription = "",
	projectUrl = "", projectImageUrl = ""
 */

function createProjectListForm(){
	const formList = [];
	
	const projectTags = $("#work-article > #projectslide > .carousel-inner > div");
	
	for(let tag of projectTags){
		const id = tag.getAttribute("db-id");
		const projectTitle = tag.querySelector(".description > div:nth-child(1) > input").value;
		const projectDescription = tag.querySelector(".description > div:nth-child(2) > textarea").innerText;
		const projectUrl = tag.querySelector(".description > div:nth-child(3) > input").value;
		const projectImageUrl = tag.querySelector(".image > div > img").src;
		
		const form = {
			id : Number(id),
			projectTitle : projectTitle,
			projectDescription : projectDescription,
			projectUrl : projectUrl,
			projectImageUrl : projectImageUrl
		}
		
		formList.push(form);	
	}
	
	
	return formList;
}


async function saveProjects(){
	const projects = createProjectListForm();
	const token = $("meta[name='_csrf']").attr("content");
	const tokenKey = $("meta[name='_csrf_header']").attr("content");

	// initialize header
	const neededHeader = {};

	neededHeader[tokenKey] = token;
	neededHeader["Content-Type"] = "application/json";
	neededHeader["Accept"] = "application/json";
	
	console.log(JSON.stringify({work : projects}));


	const result = await fetch('/admin/work', {
		method: 'POST',
		headers: neededHeader,
		body : JSON.stringify({works : projects})
	}).then(response => response.json());
	
	
	console.log(result);
	
}





(function addCustomFormSubmitEvent() {
	$("#saveToFile").submit(async function(event) {

		event.preventDefault();

		// Detect form tag and set up Form data
		const formTag = $("#saveToFile")[0];
		const imgTag = $(".my-face").siblings("img");
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



		const faceImgResponse = await fetch(imgTag.attr("src"));
		const faceImgBlob = await faceImgResponse.blob();

		// Append techs data to formData
		form.append("techs", categoryInfo);

		form.append("facePhoto", faceImgBlob);

		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");

		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader(header, token);
		});

		$.ajax({
			url: "/admin/main",
			data: form,
			cache: false,
			contentType: false,
			processData: false,
			method: "POST",
			type: "POST",
			success: function(data) {
				alert(data.message);
				window.location.replace("/admin/main");
			},
			error: function(e) {
				const response = e.responseJSON;
				alert(response.message);
			}


		}
		);
		
		saveProjects();


	});

})();




