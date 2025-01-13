
document.addEventListener("DOMContentLoaded", function () {

    let currentTitle = "";
    let currentCategory = "";
    let currentPage = 0;
    let itemsPerPageArticle = 5;
    //Hien thi blog
    fetchArticleUser(currentPage, itemsPerPageArticle);
    // hien thi for card
    // fetchArticleForCard();
    getArticleForCard()  ;

    // search tim kiem
    const searchForm = document.getElementById("form");
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang khi submit
        currentTitle = document.querySelector("input[name='titleArticle']").value;
        searchArticlesByTitleOrCategory(currentTitle, "", currentPage, itemsPerPageArticle);
        const searchUrl = new URL(window.location);
        if (currentTitle) searchUrl.searchParams.set('titleArticle', currentTitle);
        searchUrl.searchParams.delete('category');
        history.pushState(null, '', searchUrl);
    });
    const categoryLinks = document.querySelectorAll("ul.list-unstyled li");
    categoryLinks.forEach(link => {
        link.addEventListener("click", function (event) {
            event.preventDefault(); // Ngăn chặn hành động mặc định của link

            // Lấy giá trị category từ text của link
            currentCategory = this.textContent.trim();

            // Gọi hàm tìm kiếm chỉ theo category
            searchArticlesByTitleOrCategory("", currentCategory, currentPage, itemsPerPageArticle);

            // Cập nhật URL với chỉ category
            const searchUrl = new URL(window.location);
            searchUrl.searchParams.delete('titleArticle'); // Xóa giá trị title nếu có
            searchUrl.searchParams.set('category', currentCategory);
            history.pushState(null, '', searchUrl);
            console.log("Category selected:", currentCategory);
        });
    });

    //xu ly phan trang
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            if (currentTitle || currentCategory) {
                searchArticlesByTitleOrCategory(currentTitle, currentCategory, currentPage, itemsPerPageArticle);
            } else {
                fetchArticleUser(currentPage, itemsPerPageArticle);
            }
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        if (currentTitle || currentCategory) {
            searchArticlesByTitleOrCategory(currentTitle, currentCategory, currentPage, itemsPerPageArticle);
        } else {
            fetchArticleUser(currentPage, itemsPerPageArticle);
        }
    });

});
async function fetchArticleUser(page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/article/allForUser?page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không lấy được thông tin baif viet. Hãy xem lại trong articleUser.js");
            return;
        }
        const articleData = await response.json();
        displayArticleForUser(articleData.content);
        updatePaginationArtile(articleData);
    } catch (error) {
        console.error("error: ", error);
    }
}
// lay ds for card
function getArticleForCard() {
    const prevBtn = document.getElementById('btn-prev');
    const nextBtn = document.getElementById('btn-next');
    const marquee_content = document.getElementById('marquee_content');

    let currentIndex = 0;
    let totalCards = 0;
    const cardsPerView = 3;
    let cardsData = [];
    fetchArticleForCard();

    async function fetchArticleForCard() {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/article/1to6`);
            if (!response.ok) {
                console.error("Không lấy được thông tin baif viet. Hãy xem lại trong articleUser.js");
                return;
            }
            const data = await response.json();
            cardsData = data;
            totalCards = data.length;
            displayArticleForCard();
        } catch (error) {
            console.error("error: ", error);
        }
    }
    function displayArticleForCard() {
        marquee_content.innerHTML = "";
        const start = currentIndex;
        const end = Math.min(start + cardsPerView, totalCards);

        // Lặp qua dữ liệu trong khoảng [start, end)
        for (let i = start; i < end; i++) {
            const article = cardsData[i];
            let image1 = encodeURIComponent(article.image1);
            let timestamp = new Date().getTime();
            const formattedDate = new Date(article.timePost).toLocaleDateString("vi-VN", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric"
        });
            let card = `
                <div class="col-md-4">
                    <div class="card">
                      <img src="/uploadsArticle/${image1}?t=${timestamp}" class="card-img-top" alt="Post 1">
                      <div class="card-body">
                        <h5 class="card-title">
                        <a onclick="viewDetail(${article.id})">    
                        ${article.title}
                        </a>
                        </h5>
                        <p>${article.aboutBlog}</p>
                        <div style="display: flex; justify-content: space-between">
                            <p><i class="fa fa-user"></i> ${article.nameAuthor}</p>
                            <p><i class="fas fa-calendar-days"></i> ${formattedDate}</p>
                        </div>
                      </div>
                    </div>
                </div>
        `;
            marquee_content.innerHTML += card;
        }
        updateButtons();
    }
    function updateButtons() {
        prevBtn.disabled = currentIndex === 0;
        nextBtn.disabled = currentIndex + cardsPerView >= totalCards;
    }

    nextBtn.addEventListener('click', () => {
        if (currentIndex + cardsPerView < totalCards) {
            currentIndex += cardsPerView;
            displayArticleForCard();
        }
    });

    // Sự kiện nút "Prev"
    prevBtn.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex -= cardsPerView;
            displayArticleForCard();
        }
    });
}

function displayArticleForUser(article) {
    var articleData = document.getElementById("articleData");
    articleData.innerHTML = "";
    article.forEach(article => {
        const formattedDate = new Date(article.timePost).toLocaleDateString("vi-VN", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric"
        });
        let image1 = encodeURIComponent(article.image1);
        let timestamp = new Date().getTime();
        let card = `
                <article class="card mb-4">
                    <div class="row g-0 align-items-center">
                          <div class="col-md-4">
                            <img src="/uploadsArticle/${image1}?t=${timestamp}" class="img-fluid rounded-start" alt="ảnhTitle">
                          </div>
                          <div class="col-md-8">
                            <div class="card-body">
                              <h5 class="card-title">
                                  <a onclick="viewDetail(${article.id})">
                                    ${article.title}
                                   </a>
                                </h5>
                              <p class="text-infor small">
                                <span><i class="fas fa-file"></i>  ${article.category}</span> 
                                 <span><i class="fas fa-calendar-days"></i> ${formattedDate}</span>
                                 <span><i class="fa fa-user"></i> ${article.nameAuthor}</span>
                              </p>
                              <p class="card-text">${article.aboutBlog}</p>
                            </div>
                          </div>
                    </div>
                </article>
        `;
        articleData.innerHTML += card;
    });
}

async function searchArticlesByTitleOrCategory(title, category, page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/article/searchForUser?titleArticle=${encodeURIComponent(title)}&category=${encodeURIComponent(category)}&page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không tìm thấy baif viet.");
            return;
        }
        const articles = await response.json();
        console.log(articles.content)
        displayArticleForUser(articles.content);
        updatePaginationArtile(articles);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm baif viet:", error);
    }
}
// capaj nhat phan trang
function updatePaginationArtile(data) {
    const prevButton = document.getElementById("prevPage");
    const nextButton = document.getElementById("nextPage");

    prevButton.disabled = data.pageable.pageNumber === 0;
    nextButton.disabled = data.pageable.pageNumber >= data.totalPages - 1;

    document.getElementById("pageInfo").textContent = `Trang ${data.pageable.pageNumber + 1} / ${data.totalPages}`;
}

function viewDetail(idArticle) {
    localStorage.setItem("idArticle", JSON.stringify(idArticle));
    window.location.href = "/user/articleDetail"
}