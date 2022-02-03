function uploadImageBook(imageURL) {
    const inpFile = document.getElementById("inpFile");
    const previewContainer = document.getElementById("imagePreview");
    const previewImage = previewContainer.querySelector(".image-preview__image-book");
    const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
    const saveImage = document.getElementById("save-image");

    inpFile.addEventListener("change", function () {
        const file = this.files[0];

        if (file) {
            const reader = new FileReader();

            previewDefaultText.style.display = "none";
            previewImage.style.display = "block";
            saveImage.disabled = false;

            reader.addEventListener("load", function () {
                previewImage.setAttribute("src", this.result);
            });

            reader.readAsDataURL(file);
        } else {
            saveImage.disabled = true;
            previewDefaultText.style.display = null;
            previewImage.setAttribute("src", imageURL);
        }
    });
}

function uploadImageUser(imageURL) {
    const inpFile = document.getElementById("inpFile");
    const previewContainer = document.getElementById("imagePreview");
    const previewImage = previewContainer.querySelector(".image-preview__image-user");
    const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
    const saveImage = document.getElementById("save-image");

    inpFile.addEventListener("change", function () {
        const file = this.files[0];

        if (file) {
            const reader = new FileReader();

            previewDefaultText.style.display = "none";
            previewImage.style.display = "block";
            saveImage.disabled = false;

            reader.addEventListener("load", function () {
                previewImage.setAttribute("src", this.result);
            });

            reader.readAsDataURL(file);
        } else {
            saveImage.disabled = true;
            previewDefaultText.style.display = null;
            previewImage.setAttribute("src", imageURL);
        }
    });
}
