/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'custom-light-blue': '#e4ecfe', // Add your custom color,
        'fontcolor-tertiary':'#F33EFD',
        'background-tertiary':'#FFF0EE'
      }
    },
  },
  plugins: [],
}

