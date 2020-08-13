// 3rd party imports
import React from 'react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle'
import { Card } from '@material-ui/core'
import styled from 'styled-components'

// My imports

const ActiveUser = ({ username }) => {
  if (username.length > 10) {
    username = username.slice(0, 10).concat('...')
  }
  return (
    <StyledCard>
      {username} <AccountCircleIcon style={{ marginLeft: '3px' }} />
    </StyledCard>
  )
}

export default ActiveUser

// STYLING
const StyledCard = styled(Card)`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 8px;
  color: white;
  background-color: #3b3b3b;
  margin-bottom: 15px;
`
