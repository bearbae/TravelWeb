document.addEventListener("DOMContentLoaded", function (){
    var IdForImage = JSON.parse(localStorage.getItem("tourIdForImage"));
    var addImage = document.getElementById("addImage");

    addImage.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        fetch(`http://localhost:8080/api/admin/image/add/${IdForImage}`, {
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
                    window.location.href = "/admin/tour/image"
                } else{
                    alert("Loix")
                }
            })
            .catch(error => {
                console.error("Error: ", error)
                alert("Lỗi. Hãy thử lại!");
            })
    })
})