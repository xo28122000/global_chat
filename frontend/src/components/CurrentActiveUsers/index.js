// 3rd party imports
import React, { useState, useEffect } from 'react'
import styled from 'styled-components'
import { connect } from 'react-redux'
import { Link } from 'react-router-dom'
import axios from 'axios'

// My imports
import ActiveUser from '../ActiveUser'
import { setUsers } from '../../redux/actions'
import { Typography, Divider, Button } from '@material-ui/core'

const CurrentActiveUsers = ({ usersList, setUsers, setDrawerOpen, userObj }) => {
  const [users, setNewUsers] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    setIsLoading(true)
    if (usersList) {
      setNewUsers(usersList)
      setIsLoading(false)
    } else {
      axios
        .get('/allUsers')
        .then((res) => {
          if (res.data.success === true) {
            setNewUsers(res.data.payload)
            setUsers(res.data.payload)
          } else {
            setError('Error loading users')
          }
          setIsLoading(false)
        })
        .catch((e) => {
          console.warn(e.message)
          setError('Error loading users')
        })
    }
    setIsLoading(false)
    setError(null)
  }, [usersList, setUsers])

  if (isLoading) {
    return <div>Loading</div>
  }
  if (error) {
    return <div>{error}</div>
  }

  console.log(userObj)

  return (
    <ComponentContainer>
      <React.Fragment>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <Button
            variant='contained'
            color='primary'
            onClick={() => setDrawerOpen(false)}
          >
            Back
          </Button>
          <Button variant='contained' color='primary'>
            <Link style={{ color: 'black', textDecoration: 'none' }} to='/user'>
              My Profile
            </Link>
          </Button>
        </div>
        <div style={{ padding: '1rem 3rem 3rem 3rem' }}>
          <Typography
            style={{ textAlign: 'center', margin: '10px 0' }}
            variant='h5'
          >
            List of Users
            <Divider variant='middle' />
          </Typography>
          {users.length > 0 ? (
            users.map((user, index) => {
              return <ActiveUser key={index} username={user.username}/>
            })
          ) : (
            <div>No users found</div>
          )}
        </div>
      </React.Fragment>
    </ComponentContainer>
  )
}

const mapStateToProps = (state) => {
  return { usersList: state.usersList, userObj: state.userObj }
}

function mapDispatchToProps(dispatch) {
  return {
    setUsers: (usersList) => dispatch(setUsers(usersList)),
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CurrentActiveUsers)

// STYLING
const ComponentContainer = styled.div`
  padding: 1rem;
  text-align: center;
  display: flex;
  flex-direction: column;
`
