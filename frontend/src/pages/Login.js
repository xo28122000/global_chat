// 3rd party imports
import React, { useState } from 'react'

import { Redirect } from 'react-router-dom'
import { connect } from 'react-redux'

// My imports
import { login } from '../redux/actions'
import TabPanel from '../components/TabPanel'

import axios from 'axios'

const Login = (props) => {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  const handleLogin = (data) => {
    setIsLoading(true)
    const body = { username: data.username, password: data.password }
    axios
      .post('/auth/signin', body)
      .then((res) => {
        if (res.data.success === true) {
          props.login({
            username: data.username,
            password: data.password
          })
        } else {
          setError(res.data.payload.message || 'Error signing in')
        }
        setIsLoading(false)
      })
      .catch((e) => {
        console.warn(e.message)
        setError('Error signing in')
        setIsLoading(false)
      })
  }

  const handleSignup = (data) => {
    setIsLoading(true)
    const body = { username: data.username, password: data.password }
    axios
      .post('/auth/signup', body)
      .then((res) => {
        if (res.data.success === true) {
          props.login({
            username: data.username,
            password: data.password
          })
        } else {
          setError(res.data.payload.message || 'Error signing up')
        }
        setIsLoading(false)
      })
      .catch((e) => {
        console.warn(e.message)
        setError('Error signing up')
        setIsLoading(false)
      })
  }
  return (
    <div>
      {props.isUser ? <Redirect to='/home' /> : null}
      <TabPanel handleLogin={handleLogin} handleSignup={handleSignup} isLoading={isLoading} error={error}/>
    </div>
  )
}

const mapStateToProps = (state) => {
  return { isUser: state.isUser }
}
function mapDispatchToProps(dispatch) {
  return {
    login: (userObj) => dispatch(login(userObj)),
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login)
