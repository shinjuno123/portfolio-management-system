<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Junho Shin</title>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/logo.png" />

    <!-- Google font CDN -->
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"
    />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;900&display=swap"
      rel="stylesheet"
    />

    <!-- Bootstrap CSS CDN -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css" />
  </head>
  <body class="bg-dark">
    <!-- Canvas background -->
    <div style="position: fixed; width: 100%; height: 100%; z-index: -100">
      <canvas id="backgroundCanvas"></canvas>
    </div>

    <!-- social media-->
    <section class="social-media-section d-md-inline-block d-none fade-out-in">
      <div class="first-line mb-3"></div>
      <div class="icons">
        <a class="d-block mb-3 side-icon" href="https://github.com/shinjuno123"
          ><img src="${pageContext.request.contextPath}/resources/img/github.svg" />
        </a>
        <a class="d-block mb-3 side-icon" href="https://github.com/shinjuno123"
          ><img src="${pageContext.request.contextPath}/resources/img/linkedin.png" />
        </a>
        <a class="d-block mb-3 side-icon" href="https://github.com/shinjuno123"
          ><img src="${pageContext.request.contextPath}/resources/img/codepen.png" />
        </a>
      </div>
      <div class="last-line"></div>
    </section>

    <!-- add navigation bar -->
    <nav id="navbar-main" class="navbar fixed-top navbar-expand-lg py-4">
      <div class="container-fluid mx-lg-5 mx-2">
        <a
          class="navbar-brand text-light"
          style="width: 5%"
          href="#introduction"
        >
          <img
            src="${pageContext.request.contextPath}/resources/img/logo.png"
            alt="logo"
            style="width: 100%; height: auto"
          />
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <img src="${pageContext.request.contextPath}/resources/img/menu.svg" />
        </button>

        <div
          class="d-lg-flex justify-content-end text-center collapse navbar-collapse"
          id="navbarSupportedContent"
        >
          <ul class="d-lg-flex gap-lg-5 navbar-nav nav-pills">
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#aboutme">About</a>
            </li>
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#technology">Technology</a>
            </li>
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#experience">Experience</a>
            </li>
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#work">Work</a>
            </li>
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#contact">Contact</a>
            </li>
            <li class="g-col-2 nav-item">
              <a class="nav-link text-light" href="#">Resume</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- add main content part -->
    <main
      data-bs-spy="scroll"
      data-bs-target="#navbar-main"
      class="container-fluid min-vh-100"
    >
      <!-- Introduction -->
      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="introduction"
      >
        <div class="d-flex align-items-center w-75">
          <div id="introduction-article">
            <p class="mb-3">Hi, My name is</p>
            <h1 class="intro-text mb-3">Junho Shin</h1>
            <h2 class="fs-1 mb-5">I am a Junior Software Developer.</h2>
            <p class="fs-5">
              I'm a passionate junior developer. I'm so excited to learn new
              skills and work on projects every single day. Currently, I'm
              making web projects using spring-mvc or spring boot.
            </p>
          </div>
        </div>
      </section>

      <!-- About me -->
      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="aboutme"
      >
        <div class="w-75" id="aboutme-article">
          <p class="fs-2">About me</p>
          <div
            class="w-25 mb-5"
            style="height: 2px; background-color: white"
          ></div>

          <!-- about me / image -->
          <div class="d-md-flex">
            <!-- about me section -->
            <div class="mw-50">
              <p class="fs-5">
                Hi, My name is Junho. I was born and graduated my college in
                South Korea. I have B.S in computer science. Right after
                graduating my college, I moved to America and married with my
                wife who is a US citizen.
              </p>
              <p class="fs-5">My first inspiration in software development.</p>
              <p class="fs-5">What I am doing lately</p>

              <!-- Education -->
              <h3 class="fs-2 mt-5 mb-4">Education</h3>

              <table class="table text-light mb-5 pb-5 mb-lg-0 pb-lg-0">
                <tbody>
                  <tr>
                    <th scope="row"><p class="mt-3">Period</p></th>
                    <td><p class="mt-3">Mar, 2017 ~ Feb, 2023</p></td>
                  </tr>
                  <tr>
                    <th scope="row"><p class="mt-3">School</p></th>
                    <td><p class="mt-3">Konyang University</p></td>
                  </tr>
                  <tr>
                    <th scope="row"><p class="mt-3">Degree</p></th>
                    <td><p class="mt-3">B.S in Computer Engineering</p></td>
                  </tr>
                  <tr>
                    <th scope="row"><p class="mt-3">Region / Country</p></th>
                    <td><p class="mt-3">Daejoen, South Korea</p></td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- image section -->
            <div class="mw-50 d-flex align-items-center justify-content-center">
              <!-- image frame -->
              <div class="w-50">
                <img
                  class="img-fluid img-thumbnail rounded"
                  src="${pageContext.request.contextPath}/resources/img/my-picture.jpeg"
                  alt=""
                />
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- Technology -->
      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="technology"
      >
        <div class="w-75" id="technology-article">
          <h3 class="fs-2 mb-5">My Technology</h3>
          <p>
            ⭐ is the score of proficiency in the corresponding skills
            relatively(from 1 to 5)
          </p>
          <!-- Add toggle buttons between frontend / backend -->
          <div class="row text-center ms-1 category-buttons">
            <div
              class="col col-md-2 border-bottom border-light py-3"
              data-bs-target="#frontEnd"
            >
              <div>Front-end</div>
            </div>
            <div class="col col-md-2 py-3" data-bs-target="#backEnd">
              <div>Back-end</div>
            </div>
          </div>

          <!-- technology list -->
          <div class="row ms-1 category" id="frontEnd">
            <ul class="d-flex flex-wrap">
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">HTML ⭐4</div>
              </li>
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">CSS ⭐3</div>
              </li>
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">Javascript ⭐4</div>
              </li>
            </ul>
          </div>

          <div class="row ms-1 category" id="backEnd">
            <ul class="d-flex flex-wrap">
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">Java Spring</div>
              </li>
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">Django</div>
              </li>
              <li
                class="px-3 m-2 border border-primary rounded"
                style="background: #0a2647"
              >
                <div class="text-center my-3">NodeJS</div>
              </li>
            </ul>
          </div>

          <div class="category-border"></div>
        </div>
      </section>

      <!-- Experience -->
      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="experience"
      >
        <div class="w-75" id="experience-article">
          <h3 class="fs-2 mb-5">Where I've worked</h3>
          <!-- Add toggle buttons between frontend / backend -->
          <div class="row text-center ms-1 category-buttons">
            <div
              class="col col-md-2 border-bottom border-light py-3"
              data-bs-target="#work1"
            >
              <div>Work1</div>
            </div>
          </div>

          <!-- Experience conetent -->
          <div class="row ms-1 category" id="work1">
            <ul class="d-flex flex-wrap">
              <li class="px-3">
                <div class="text-center my-3">
                  Upcoming soon now I'm trying to get my first job after
                  graduation! My dream for now is to fill this section as fast
                  as I can!
                </div>
              </li>
            </ul>
          </div>

          <div class="category-border"></div>
        </div>
      </section>

      <!-- Work : Some thigns I've built-->

      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="work"
      >
        <div
          class="w-75 h-100 d-flex flex-column align-items-start"
          id="work-article"
        >
          <h3 class="fs-2 mb-4">Some things I've built</h3>

          <!-- project cards section -->

          <div
            class="position-relative w-100 card-section carousel slide"
            style="min-height: 40rem"
            id="carouselExampleIndicators"
          >
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="0"
                class="active"
                aria-current="true"
                aria-label="Slide 1"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="1"
                aria-label="Slide 2"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to="2"
                aria-label="Slide 3"
              ></button>
            </div>

            <!-- carousel inner -->
            <div class="w-100 h-100 carousel-inner">
              <!-- carousel items -->
              <div class="carousel-item card-body active" style="height: 40rem">
                <div class="row h-100">
                  <div class="col-md align-items-center d-md-flex ms-md-5">
                    <div class="glass position-absolute">
                      <input
                        type="hidden"
                        value="https://github.com/shinjuno123/tomato-disease-detector"
                      />
                    </div>
                    <img
                      class="rounded project-image"
                      style="width: 100%"
                      src="${pageContext.request.contextPath}/resources/img/sample-project-image.png"
                      alt="project-image"
                    />
                  </div>

                  <div
                    class="text-end col-md justify-content-center flex-column d-md-flex me-md-5"
                  >
                    <h3 class="fs-6 mb-2">Featured Project</h3>
                    <h3 class="fs-3 mb-3">Tomato Disease Detector1</h3>
                    <p>
                      We developed tomato disease detector web service using
                      tomato disease images from AI HUB(https://aihub.or.kr/)
                    </p>
                  </div>
                </div>
              </div>

              <div class="carousel-item card-body" style="height: 40rem">
                <div class="row h-100">
                  <div class="col-md align-items-center d-md-flex ms-md-5">
                    <div class="glass position-absolute">
                      <input
                        type="hidden"
                        value="https://github.com/shinjuno123/tomato-disease-detector"
                      />
                    </div>
                    <img
                      class="rounded project-image"
                      style="width: 100%"
                      src="${pageContext.request.contextPath}/resources/img/sample-project-image.png"
                      alt="project-image"
                    />
                  </div>

                  <div
                    class="text-end col-md justify-content-center flex-column d-md-flex me-md-5"
                  >
                    <h3 class="fs-6 mb-2">Featured Project</h3>
                    <h3 class="fs-3 mb-3">Tomato Disease Detector2</h3>
                    <p>
                      We developed tomato disease detector web service using
                      tomato disease images from AI HUB(https://aihub.or.kr/)
                    </p>
                  </div>
                </div>
              </div>

              <div class="carousel-item card-body" style="height: 40rem">
                <div class="row h-100">
                  <div class="col-md align-items-center d-md-flex ms-md-5">
                    <div class="glass position-absolute">
                      <input
                        type="hidden"
                        value="https://github.com/shinjuno123/tomato-disease-detector"
                      />
                    </div>
                    <img
                      class="rounded project-image"
                      style="width: 100%"
                      src="${pageContext.request.contextPath}/resources/img/sample-project-image.png"
                      alt="project-image"
                    />
                  </div>

                  <div
                    class="text-end col-md justify-content-center flex-column d-md-flex me-md-5"
                  >
                    <h3 class="fs-6 mb-2">Featured Project</h3>
                    <h3 class="fs-3 mb-3">Tomato Disease Detector3</h3>
                    <p>
                      We developed tomato disease detector web service using
                      tomato disease images from AI HUB(https://aihub.or.kr/)
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <!-- prev / next buttons -->
            <button
              class="carousel-control-prev left-button"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>

            <button
              class="carousel-control-next right-button"
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
      </section>

      <!-- Work : Other noteworthy projects -->
      <section
        class="container-lg d-flex align-items-center justify-content-center main"
        id="noteworthyProject"
      >
        <div class="w-100" id="noteworthProjectArticle">
          <h2 class="fs-2 text-center mb-3">Other Noteworthy Projects</h2>
          <div class="text-center mb-5">
            <a
              class="d-inline-block text-decoration-none view-archive"
              href="#"
            >
              view the archive <span class="underline"></span>
            </a>
          </div>

          <!-- other noteworthy projects boxes -->
          <!-- 1 -->
          <div class="row gx-4 gy-4 mb-5 pb-3">
            <div class="col-lg-4 col-md-6">
              <div
                class="card text-light-subtle border border-primary project-box"
                style="background-color: #0a2647"
              >
                <div
                  class="d-flex justify-content-center align-items-center h-100"
                >
                  <div class="card-body m-2">
                    <input
                      type="hidden"
                      value="https://github.com/shinjuno123/fish-raising-game"
                    />
                    <span class="material-symbols-outlined folder mb-3">
                      folder
                    </span>
                    <h4 class="card-title fs-5 mb-3">Fish Raising Game</h4>
                    <p class="card-text">
                      This game is fish-raising-game. "user" fish can eat other
                      "mob" fishes if the size of "mob" fishes are smaller than
                      user fish. You can win this game when you reach at size
                      300 within 200 sec.
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <!-- 2 -->
            <div class="col-lg-4 col-md-6">
              <div
                class="card text-light-subtle border border-primary project-box"
                style="background-color: #0a2647"
              >
                <div
                  class="d-flex justify-content-center align-items-center h-100"
                >
                  <div class="card-body m-2">
                    <input
                      type="hidden"
                      value="https://github.com/shinjuno123/fish-raising-game"
                    />
                    <span class="material-symbols-outlined folder mb-3">
                      folder
                    </span>
                    <h4 class="card-title fs-5 mb-3">Fish Raising Game</h4>
                    <p class="card-text">
                      This game is fish-raising-game. "user" fish can eat other
                      "mob" fishes if the size of "mob" fishes are smaller than
                      user fish. You can win this game when you reach at size
                      300 within 200 sec.
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <!-- 3 -->
            <div class="col-lg-4 col-md-6">
              <div
                class="card text-light-subtle border border-primary project-box"
                style="background-color: #0a2647"
              >
                <div
                  class="d-flex justify-content-center align-items-center h-100"
                >
                  <div class="card-body m-2">
                    <input
                      type="hidden"
                      value="https://github.com/shinjuno123/fish-raising-game"
                    />
                    <span class="material-symbols-outlined folder mb-3">
                      folder
                    </span>
                    <h4 class="card-title fs-5 mb-3">Fish Raising Game</h4>
                    <p class="card-text">
                      This game is fish-raising-game. "user" fish can eat other
                      "mob" fishes if the size of "mob" fishes are smaller than
                      user fish. You can win this game when you reach at size
                      300 within 200 sec.
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <!-- 4 -->
            <div class="col-lg-4 col-md-6">
              <div
                class="card text-light-subtle border border-primary project-box"
                style="background-color: #0a2647"
              >
                <div
                  class="d-flex justify-content-center align-items-center h-100"
                >
                  <div class="card-body m-2">
                    <input
                      type="hidden"
                      value="https://github.com/shinjuno123/fish-raising-game"
                    />
                    <span class="material-symbols-outlined folder mb-3">
                      folder
                    </span>
                    <h4 class="card-title fs-5 mb-3">Fish Raising Game</h4>
                    <p class="card-text">
                      This game is fish-raising-game. "user" fish can eat other
                      "mob" fishes if the size of "mob" fishes are smaller than
                      user fish. You can win this game when you reach at size
                      300 within 200 sec.
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <!-- 5 -->
            <div class="col-lg-4 col-md-6">
              <div
                class="card text-light-subtle border border-primary project-box"
                style="background-color: #0a2647"
              >
                <div
                  class="d-flex justify-content-center align-items-center h-100"
                >
                  <div class="card-body m-2">
                    <input
                      type="hidden"
                      value="https://github.com/shinjuno123/fish-raising-game"
                    />
                    <span class="material-symbols-outlined folder mb-3">
                      folder
                    </span>
                    <h4 class="card-title fs-5 mb-3">Fish Raising Game</h4>
                    <p class="card-text">
                      This game is fish-raising-game. "user" fish can eat other
                      "mob" fishes if the size of "mob" fishes are smaller than
                      user fish. You can win this game when you reach at size
                      300 within 200 sec.
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <!-- noteworthy project end -->
          </div>

          <!-- show more button -->
          <div class="text-center mb-5 pb-3">
            <button class="additional-information" id="show-more">
              Show More
            </button>
          </div>
        </div>
      </section>

      <!-- Contact -->
      <section
        class="container-lg d-flex flex-column align-items-center justify-content-center main"
        id="contact"
      >
        <div class="d-flex align-items-center text-center w-75 mb-5">
          <div id="contact-article">
            <p class="mb-2">What's next?</p>
            <h1 class="fs-1 fw-bolder intro-text mb-4">Get in touch</h1>
            <p class="fs-5">
              I'm now looking for new opportunities after graduating my college.
              My box is always open. Please contact to this email or phone
              number or any means of contact written in this page if you have
              anything you want to tell me!
            </p>
            <p class="fs-3 fw-bold">Always Welcome!</p>
          </div>
        </div>
        <!-- Say hello button -->
        <div class="text-center mb-5 pb-3">
          <button class="additional-information mb-3" id="send-mail">
            Say Hello!
          </button>
          <p>Email : shinjuno123@gmail.com</p>
        </div>
      </section>
    </main>

    <footer class="container-fluid text-center py-4 bg-dark">
      Design & Fullstack Development by Junho Shin in 2023
    </footer>

    <!-- Bootstrap JS -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
      crossorigin="anonymous"
    ></script>

    <!-- Jquery CDN -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

    <!-- Paper js -->
    <script
      type="text/paperscript"
      src="${pageContext.request.contextPath}/resources/js/paper.js"
      canvas="backgroundCanvas"
    ></script>
    <script src="${pageContext.request.contextPath}/resources/js/paper-full.min.js"></script>

    <!-- JS -->
    <script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
  </body>
</html>