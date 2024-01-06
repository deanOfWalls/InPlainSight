let selectedImageSrc = null;
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
                handleEncoding(img.src);
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

// Function to show decoy images
function showDecoyImages() {
    if (encodeMode) {
        const decoyImages = document.getElementById('decoyImages');
        if (decoyImages) {
            decoyImages.style.display = 'block';
        }
    }
}

// Function to handle encoding of an image with a decoy image
function encodeImageWithDecoy(selectedImageSrc, decoyImageSrc) {
    // Implement encoding logic here using selectedImageSrc and decoyImageSrc
    // Once encoding is done, you can set the result as the source of stegoImage
    // For now, we will just set the source to the selectedImageSrc as a placeholder
    const stegoImage = document.getElementById('stegoImage');
    stegoImage.src = selectedImageSrc;
    stegoImage.style.display = 'block';
}

// Function to handle the encoding process
function handleEncoding(imageSrc) {
    // Check if encode mode is active
    if (encodeMode) {
        selectedImageSrc = imageSrc;
        showDecoyImages();
    }
}

// Function to handle the click event on a decoy image
function handleDecoyImageClick(decoyImageSrc) {
    // Check if an image is selected for encoding
    if (selectedImageSrc) {
        // Encode the selected image with the chosen decoy image
        encodeImageWithDecoy(selectedImageSrc, decoyImageSrc);
        // Display the download link for the encoded image
        const downloadLink = document.getElementById('downloadLink');
        downloadLink.style.display = 'block';
        downloadLink.href = selectedImageSrc; // Set download link to the encoded image
        downloadLink.download = 'encoded_image.png'; // Set the filename for download
    }
}

// Rest of the code remains the same...

// Listen for file input change
document.getElementById('fileInput').addEventListener('change', function () {
    showImages(this.files);
});

// Close the overlay when clicking outside the decoy images
const overlay = document.getElementById('overlay');
overlay.addEventListener('click', (event) => {
    if (event.target === overlay) {
        hideOverlay();
    }
});

// Initially hide the overlay, selected image, and download link
overlay.style.display = 'none';
document.getElementById('selectedImage').style.display = 'none';
document.getElementById('downloadLink').style.display = 'none';
