/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.{html,js}"
  ],
  theme: {
    extend: {
      colors: {
        "page": '#E7EDFF',
        "primary-color": '#769CFF',
        "bg-inputs": '#F9F9F9',
        "color-task": '#98B5FF'
      }
        
    },
  },
  plugins: [],
}

