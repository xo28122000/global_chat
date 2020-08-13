// 3rd party imports
import React from 'react'
import { ThemeProvider } from '@material-ui/core/styles'

// my imports
import './styles/App.css'
import { lightTheme, darkTheme } from './styles/theme'
import { Routes } from './Routes'

const App = () => {
  const [darkThemeActivated, setDarkThemeActivated] = React.useState(false)
  const handleThemeToggle = () => {
    setDarkThemeActivated(!darkThemeActivated)
  }

  return (
    <ThemeProvider theme={darkThemeActivated ? darkTheme : lightTheme}>
      <Routes handleThemeToggle={handleThemeToggle}/>
    </ThemeProvider>
  )
}

export default App
