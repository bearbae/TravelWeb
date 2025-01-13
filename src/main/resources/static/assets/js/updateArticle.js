document.addEventListener('DOMContentLoaded', function (){
    var article = JSON.parse(localStorage.getItem('article'));
    let imageName1 = encodeURIComponent(article.image1);
    let imageName2 = encodeURIComponent(article.image2);
    let imageName3 = encodeURIComponent(article.image3);
    let imageName4 = encodeURIComponent(article.image4);

    document.getElementById("title").value = article.title;
    document.getElementById("content1").value = article.content1;
    document.getElementById("content2").value = article.content2;
    document.getElementById("nameAuthor").value = article.nameAuthor;
    document.getElementById("aboutBlog").value = article.aboutBlog;
    document.getElementById("category").value = article.category;
    document.getElementById("timePost").value = article.timePost;
    document.getElementById("currentImage1").innerHTML = `
            <img style="width: 150px; margin: 5px 0" src="/uploadsArticle/${imageName1}" alt="${article.image1}"/>
        `;
    document.getElementById("currentImage2").innerHTML = `
            <img style="width: 150px; margin: 5px 0" src="/uploadsArticle/${imageName2}" alt="${article.image2}"/>
        `;
    document.getElementById("currentImage3").innerHTML = `
            <img style="width: 150px; margin: 5px 0" src="/uploadsArticle/${imageName3}" alt="${article.image3}"/>
        `;
    document.getElementById("currentImage4").innerHTML = `
            <img style="width: 150px; margin: 5px 0" src="/uploadsArticle/${imageName4}" alt="${article.image4}"/>
        `;



    var updateArticle = document.getElementById("updateArticle");
    updateArticle.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        fetch(`http://localhost:8080/api/admin/article/update/${article.id}`, {
            method: 'PUT',
            body: formData
        }).then(response => {
            if(!response.ok){
                throw new Error("Khong co phan hoi")
            }
            return response.json();
        }).then(data =>{
            console.log(data);
            if (data){
                alert("Cập nhật bài viết với id = "+ article.id + " thành công");
                window.location.href = "/admin/article";
            }
        }).catch(error => {
            console.error(error);
        })

    })
})