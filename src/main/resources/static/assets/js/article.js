
// // Lấy thông tin tất cả baif vieet

document.addEventListener("DOMContentLoaded", function (){
    fetchArticles() ;
    const searchForm = document.getElementById("form")
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang khi submit
        const searchInput = document.querySelector("input[name='key']").value;
        searchArticlesByTitle(searchInput); // Tìm kiếm bài viết theo từ khóa
        const searchUrl = new URL(window.location);
        searchUrl.searchParams.set('title', searchInput);
        history.pushState(null, '', searchUrl);
    });
});
async function fetchArticles() {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/article/all`);
        if (!response.ok) {
            console.error("Không lấy được thông tin bai viets. Hãy xem lại trong article.js");
            return;
        }
        const article = await response.json();
        console.log(article);
        displayArticle(article);
    } catch (error) {
        console.error("error: ", error);
    }
}
async function searchArticlesByTitle(title) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/article/search?title=${encodeURIComponent(title)}`);
        if (!response.ok) {
            console.error("Không tìm thấy bài viết.");
            return;
        }
        const articles = await response.json();
        displayArticle(articles);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm bài viết:", error);
    }
}
function displayArticle(articles) {
    const articleData = document.getElementById('articleData');
    articleData.innerHTML = "";
    articles.forEach( article =>{
        let imageName1 = encodeURIComponent(article.image1);
        let imageName2 = encodeURIComponent(article.image2);
        let imageName3 = encodeURIComponent(article.image3);
        let imageName4 = encodeURIComponent(article.image4);

        let timestamp = new Date().getTime();
        let row = `
                            <tr>
                                <td>${article.id}</td>
                                <td>${article.title}</td>
                                <td>${article.content1}</td>
                                <td>${article.content2}</td>
                                <td>${article.nameAuthor}</td>
                                <td>${article.aboutBlog}</td>
                                <td>${article.timePost}</td>
                                <td>${article.category}</td>
                                <td><img src="/uploadsArticle/${imageName1}?t=${timestamp}" alt="${article.title}" style="max-width: 100%;height: auto; object-fit: cover;" width="100" /></td>
                                <td><img src="/uploadsArticle/${imageName2}?t=${timestamp}" alt="${article.title}" style="max-width: 100%;height: auto; object-fit: cover;" width="100" /></td>
                                <td><img src="/uploadsArticle/${imageName3}?t=${timestamp}" alt="${article.title}" style="max-width: 100%;height: auto; object-fit: cover;" width="100" /></td>
                                <td><img src="/uploadsArticle/${imageName4}?t=${timestamp}" alt="${article.title}" style="max-width: 100%;height: auto; object-fit: cover;" width="100" /></td>
                                <td>
                                    <button class="btn btn-success" onclick="editArticle(${article.id})">Sửa</button>
                                    <button class="btn btn-danger" onclick="deleteArticle(${article.id})">Xóa</button>
                                </td>
                            </tr>
                        `;
        articleData.innerHTML += row;
    })
}


// Cập nhật bài viết
function editArticle(articleId){
    fetch(`http://localhost:8080/api/admin/article/${articleId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin baif viết!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("article", JSON.stringify(data));
            window.location.href = "/admin/article/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteArticle(articleId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/article/delete/${articleId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa bài viết thành công.") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}