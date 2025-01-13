document.addEventListener("DOMContentLoaded", function (){

    var addArticle = document.getElementById("addArticle");

    addArticle.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        fetch(`http://localhost:8080/api/admin/article/add`, {
            method: "POST",
            body: formData
        })
            .then(response => {
                if(!response.ok){
                    throw new Error("Không có phản hồi!")
                }
                return response.text();
            })
            .then(data => {
                if(data){
                    alert(data)
                    window.location.href = "/admin/article"
                } else{
                    alert("Tiêu đề bài viết đã tồn tại! Hãy thử lại.")
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})