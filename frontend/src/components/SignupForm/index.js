// 3rd party imports
import * as React from 'react'
import { Formik, Form } from 'formik'
import {
  TextField,
  Button,
  makeStyles,
  InputAdornment,
  IconButton,
  FormControl,
} from '@material-ui/core'
import { Visibility, VisibilityOff } from '@material-ui/icons'
import * as yup from 'yup'

const SignupForm = ({ submit, isLoading, error }) => {
  const loginValidationScheme = yup.object().shape({
    username: yup.string().max(255).required('Required'),
    password: yup
      .string()
      .min(6, 'Password is must be 6 characters')
      .max(255)
      .required('Required'),
  })

  // STYLING
  const useStyles = makeStyles({
    form: {
      margin: '10px auto',
      height: '300px',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
    },
    box: {
      width: '200px',
      marginBottom: '1.5rem',
    },
  })
  const classes = useStyles()

  // ~ Logic
  const [showPassword, handleShowPassword] = React.useState(false)

  if(isLoading) {
    return <div>Loading</div>
  }

  return (
    <React.Fragment>
      <Formik
        initialValues={{ username: '', password: '' }}
        onSubmit={async (data, { setSubmitting }) => {
          setSubmitting(true)
          // make async call
          await submit(data)
          setSubmitting(false)
        }}
        validationSchema={loginValidationScheme}
      >
        {({
          values,
          isSubmitting,
          handleChange,
          handleBlur,
          touched,
          errors,
        }) => (
          <Form className={classes.form}>
            <TextField
              placeholder='username'
              className={classes.box}
              name='username'
              label='username'
              error={errors.username ? true : false}
              value={values.username}
              onChange={handleChange}
              onBlur={handleBlur}
              helperText={
                errors.username && touched.username && errors.username
              }
            />
            <TextField
              placeholder='password'
              className={classes.box}
              label='password'
              name='password'
              error={errors.password ? true : false}
              helperText={
                errors.password && touched.password && errors.password
              }
              type={showPassword ? 'text' : 'password'}
              value={values.password}
              onChange={handleChange}
              onBlur={handleBlur}
              InputProps={{
                endAdornment: (
                  <InputAdornment position='end'>
                    <IconButton
                      aria-label='toggle password visibility'
                      onClick={() => {
                        handleShowPassword(!showPassword)
                      }}
                    >
                      {showPassword ? <Visibility /> : <VisibilityOff />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
            {error && <p style={{color: 'red'}}>{error}</p>}
            <FormControl>
              <Button
                disabled={isSubmitting}
                variant='contained'
                color='primary'
                type='submit'
                style={{ marginTop: '1.5rem' }}
              >
                Signup
              </Button>
            </FormControl>
            {/* <pre>{JSON.stringify(values, null, 2)}</pre> */}
          </Form>
        )}
      </Formik>
    </React.Fragment>
  )
}

export default SignupForm
