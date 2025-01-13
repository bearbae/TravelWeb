document.addEventListener("DOMContentLoaded", function (){

    var imageOfTourData = JSON.parse(localStorage.getItem("imageOfTourData"));

    if(imageOfTourData){
        let imageName1 = encodeURIComponent(imageOfTourData.img1);
        let imageName2 = encodeURIComponent(imageOfTourData.img2);
        let imageName3 = encodeURIComponent(imageOfTourData.img3);
        let imageName4 = encodeURIComponent(imageOfTourData.img4);
        let imageName5 = encodeURIComponent(imageOfTourData.img5);


        document.getElementById("currentImage1").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsTour/${imageName1}" alt="${imageOfTourData.img1}"/>
        `;
        document.getElementById("currentImage2").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsTour/${imageName2}" alt="${imageOfTourData.img2}"/>
        `;
        document.getElementById("currentImage3").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsTour/${imageName3}" alt="${imageOfTourData.img3}"/>
        `;
        document.getElementById("currentImage4").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsTour/${imageName4}" alt="${imageOfTourData.img4}"/>
        `;
        document.getElementById("currentImage5").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsTour/${imageName5}" alt="${imageOfTourData.img5}"/>
        `;
    }
    // localStorage.removeItem("imageOfTourData");

    var updateImageOfTour = document.getElementById("updateImageOfTour");
    updateImageOfTour.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        console.log(formData);

        fetch(`http://localhost:8080/api/admin/image/update/${imageOfTourData.id}`, {
            method: 'PUT',
            body: formData
        }).then(response =>{
            if(!response.ok){
                throw new Error("ErrorUpdate");
            }
            return response.json();
        })
            .then(data => {
                if(data){
                    alert("Cập nhật thành công!")
                    window.location.href = "/admin/tour/image";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Ảnh này đã tồn tại. Hãy thử lại!");
            })
    })
})

