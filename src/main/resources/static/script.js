let selectedImageFile = null;
let selectedDecoyImageFile = null;
let encodeMode = false;

// Function to display selected local images
function showImages(files) {
    const gallery = document.getElementById('imageGallery');
    gallery.innerHTML = ''; // Clear existing images

    for (const file of files) {
        if (file.type.startsWith('image/')) { // Check if the file is an image
            const img = document.createElement('img');
            img.src = URL.createObjectURL(file);
            img.title = file.name;
            img.classList.add('thumbnail');

            // Create a container for each thumbnail
            const thumbnailContainer = document.createElement('div');
            thumbnailContainer.classList.add('thumbnail-container');
            thumbnailContainer.appendChild(img);

            // Add filename and file size under each thumbnail
            const fileInfo = document.createElement('div');
            fileInfo.classList.add('file-info');
            fileInfo.textContent = `${file.name} (${(file.size / 1024).toFixed(2)} kB)`;
            thumbnailContainer.appendChild(fileInfo);

            // Add Encode and Extract buttons for each image
            const encodeButton = document.createElement('button');
            encodeButton.textContent = 'Encode';
            encodeButton.addEventListener('click', function() {
                handleEncoding(file);
            });
            thumbnailContainer.appendChild(encodeButton);

            const extractButton = document.createElement('button');
            extractButton.textContent = 'Extract';
            extractButton.addEventListener('click', function() {
                handleExtraction(img.src);
            });
            thumbnailContainer.appendChild(extractButton);

            gallery.appendChild(thumbnailContainer);
        }
    }
}

// Function to show decoy images and buttons
function showDecoyImages() {
    if (encodeMode) {
        const decoyImages = document.getElementById('decoyImages');
        if (decoyImages) {
            decoyImages.style.display = 'block';
        }
    }
}

// Function to handle the encoding process
function handleEncoding(file) {
    encodeMode = true;
    selectedImageFile = file;
    document.getElementById('overlay').style.display = 'block';
    showDecoyImages();
}

// Function to handle the click event on a decoy image
function handleDecoyImageClick(decoyImageSrc) {
    // Fetch the actual decoy image file based on decoyImageSrc
    fetch(decoyImageSrc)
    .then(response => response.blob())
    .then(blob => {
        selectedDecoyImageFile = new File([blob], decoyImageSrc.split('/').pop(), {type: 'image/png'});
        encodeImageWithDecoy();
    })
    .catch(error => console.error('Error fetching decoy image:', error));
}

// Function to encode an image with a decoy image
function encodeImageWithDecoy() {
    let formData = new FormData();
    formData.append('decoyImage', selectedDecoyImageFile);
    formData.append('secretImage', selectedImageFile);

    fetch('/api/steganography/encode', {
        method: 'POST',
        body: formData
    })
    .then(response => response.blob())
    .then(data => {
        const url = URL.createObjectURL(data);
        const downloadLink = document.getElementById('downloadLink');
        downloadLink.href = url;
        downloadLink.download = 'encoded_image.png';
        downloadLink.style.display = 'block';
    })
    .catch(error => console.error('Error encoding image:', error));
}

// Function to handle the extraction process
function handleExtraction(imageSrc) {
    // Implement AJAX request to extract the image
    console.log('Extracting from', imageSrc);
    // Example: AJAX call to your backend endpoint /api/steganography/extract
}

// Add event listeners for decoy images and file input
window.onload = function() {
    document.getElementById('selectedDecoyImage1').addEventListener('click', function() { handleDecoyImageClick('images/decoy1.png'); });
    document.getElementById('selectedDecoyImage2').addEventListener('click', function() { handleDecoyImageClick('images/decoy2.png'); });
    document.getElementById('selectedDecoyImage3').addEventListener('click', function() { handleDecoyImageClick('images/decoy3.png'); });
    document.getElementById('selectedDecoyImage4').addEventListener('click', function() { handleDecoyImageClick('images/decoy4.png'); });

    document.getElementById('fileInput').addEventListener('change', function () {
        showImages(this.files);
    });
};
