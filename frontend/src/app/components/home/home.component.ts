import { Component, OnInit, ElementRef, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  ngOnInit(): void {
    document.addEventListener('DOMContentLoaded', (event) => {
      // Array with texts to type in typewriter
      const dataText = [
        'Utrecht.',
        'Full Service.',
        'Webdevelopment.',
        'Wij zijn Codefield!',
      ];

      // Type one text in the typewriter
      // Keeps calling itself until the text is finished
      function typeWriter(text: string, i: number, fnCallback: () => void) {
        // Check if text isn't finished yet
        if (i < text.length) {
          // Add next character to h1
          document.querySelector('h1')!.innerHTML =
            text.substring(0, i + 1) + '<span aria-hidden="true"></span>';

          // Wait for a while and call this function again for the next character
          setTimeout(() => {
            typeWriter(text, i + 1, fnCallback);
          }, 100);
        }
        // Text finished, call callback if there is a callback function
        else if (typeof fnCallback === 'function') {
          // Call callback after timeout
          setTimeout(fnCallback, 700);
        }
      }

      // Start a typewriter animation for a text in the dataText array
      function startTextAnimation(i: number) {
        if (typeof dataText[i] === 'undefined') {
          setTimeout(() => {
            startTextAnimation(0);
          }, 20000);
        }

        // Check if dataText[i] exists
        if (i < dataText.length) {
          // Text exists! Start typewriter animation
          typeWriter(dataText[i], 0, () => {
            // After callback (and the whole text has been animated), start the next text
            startTextAnimation(i + 1);
          });
        }
      }

      // Start the text animation
      console.log('vrw');
      startTextAnimation(0);
    });
  }
}
