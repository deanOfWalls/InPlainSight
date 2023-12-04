# In-Plain-Sight: Image Steganography and Encryption Project

## Overview
This project is designed as a learning exercise to explore the concepts of hashing, steganography, and encryption. It focuses on the practical application of these concepts in Java, demonstrating how to securely hide and encrypt an image within another image.

## Key Concepts
- **Hashing**: Utilizing SHA-256 for secure password hashing.
- **Steganography**: Implementing Least Significant Bit (LSB) steganography to embed secret images within a decoy image.
- **Encryption**: Using AES encryption to secure the secret image before embedding it into the decoy image.

## Functionality
- **Password Management**: Securely hash and verify passwords using SHA-256.
- **Image Encryption**: Encrypt and decrypt images using AES encryption.
- **Image Steganography**: Embed an encrypted image within another image and extract it.

## Learning Objectives
- Understand and implement basic cryptographic functions in Java.
- Explore image processing techniques for steganography.
- Develop practical skills in handling binary data and image files.

## How to Use
1. **Set Up Password**: Create an encryption password which will be hashed for security.
2. **Encrypt and Hide Image**: Select a secret image to encrypt and hide within a decoy image.
3. **Reveal Hidden Image**: Extract and decrypt the hidden image from the decoy image.

## Technologies Used
- Java
- Bouncy Castle API (for encryption)
- Java Advanced Imaging (JAI) or similar libraries (for image processing)

## Disclaimer
This project is intended for educational purposes and should not be used as a basis for real-world security applications without further modifications and security considerations.

## Future Enhancements
- Improve the user interface for easier interaction.
