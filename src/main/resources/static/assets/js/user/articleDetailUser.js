document.addEventListener("DOMContentLoaded", function () {
    var idArticle = JSON.parse(localStorage.getItem("idArticle"));

    fetchArticleDetal();

    var form = document.getElementById("formComment");
    form.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        fetch(`http://localhost:8080/api/addComment/idArticle/${idArticle}`, {
            method: "POST",
            body: formData
        }).then(response => {
            if(!response.ok){
                throw new Error("Loi khong phan hoi");
            }
            return response.text();
        }).then(data => {
                if(data) {
                    // alert("Thêm bình luận thành công");
                    window.location.reload();
                }
                else {
                    console.log("Thêm bl thất bại");
                }
            }).catch(error => {
            console.error("Error: ", error)
            alert("Lỗi. Hãy thử lại!");
        })
    })

})
async  function fetchArticleDetal() {
    var idArticle = JSON.parse(localStorage.getItem("idArticle"));

    const response = await fetch(`http://localhost:8080/api/admin/article/${idArticle}`)

    if(!response.ok){
        console.error("Khoong nhan duoc phan hoi");
        alert("error");
    } else {

        const data = await response.json();
        const formattedDate = new Date(data.timePost).toLocaleDateString("vi-VN", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric"
        });
        let imageName1 = encodeURIComponent(data.image1);
        let imageName2 = encodeURIComponent(data.image2);
        let imageName3 = encodeURIComponent(data.image3);
        var dataBlog = document.getElementById("dataBlog");
        var contentTitle = document.getElementById("contentTitle");
        contentTitle.innerHTML = `
                  <div class="title">
                    <h1>${data.title}</h1>
                    <h7>
                      <span><i class="fa fa-clock"></i> : ${formattedDate}</span>
                      <span><i class="fa fa-user"></i> ${data.nameAuthor}</span>
                    </h7>
                  </div>
                  <div class="imageTitle">
                    <img src="/uploadsArticle/${imageName3}">
                   </div>
      `;
        dataBlog.innerHTML = `
                <p>${data.content1}</p>
              <img src="/uploadsArticle/${imageName1}" alt="Blog Image" class="blog-image">
              <p>${data.content2}</p>
              <div class="box-img">
                <img src="/uploadsArticle/${imageName2}" alt="Blog Image" class="imgOfBox">
                <img src="/uploadsArticle/${imageName3}" alt="Blog Image" class="imgOfBox">
              </div>
      `;

    }
    getComment(idArticle);

}

async function getComment(idArticle){
    const response = await fetch(`http://localhost:8080/api/all/idArticle/${idArticle}`);
    if(!response.ok) {
        console.log("Khoog nhan duoc pahan hoi comment");
    } else {
        var data = await response.json();
        var dataComment = document.getElementById("dataComment");
        data.forEach(comment => {
            const formattedDate = new Date(comment.date).toLocaleDateString("vi-VN", {
                day: "2-digit",
                month: "2-digit",
                year: "numeric"
            });

            const formattedTime = new Date(`1970-01-01T${comment.time}`).toLocaleTimeString("en-US", {
                hour: "2-digit",
                minute: "2-digit",
                hour12: true
            });
            const commentHTML = `
                <div class="comment">
                    <div style="display: flex; justify-content: space-between">
                        <h4>${comment.nameUser}</h4>
                        <p><small>${formattedDate} lúc ${formattedTime}</small></p>
                    </div>
                    <p>${comment.comment}</p>
                </div>
            `;
            dataComment.innerHTML += commentHTML;
        });
    }
}
