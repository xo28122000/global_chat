import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import SvgIcon from "@material-ui/core/SvgIcon";
import { useTheme } from "@material-ui/core";
import "./styles.css";
import axios from "axios";
import { login } from "../../redux/actions";
import { connect } from "react-redux";
import { Redirect } from "react-router-dom";
import { Link } from "react-router-dom";

const User = ({ userObj, isUser }, props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [oldPassword, setOldPassword] = useState("");
  const [verifyNewPassword, setVerifyNewPassword] = useState("");

  const [newPassword, setNewPassword] = useState("");
  const [passwordError, setPasswordError] = useState(false);
  const [newPasswordError, setNewPasswordError] = useState(false);

  const [error, setError] = useState(null);

  const [anchorEl, setAnchorEl] = useState(null);
  const [showChangePassword, setShowChangePassword] = useState(false);

  const handleChangePassword = () => {
    handleVerifyPassword();

    const body = {
      username: username,
      password: oldPassword,
      newPassword: newPassword
    };

    axios
      .post("/auth/updateUser", body)

      .then(res => {
        console.log(res);
        if (res.data.success) {
          console.log(res.data.BaseResponse);
          console.log("password changed succesfully!");
          console.log(res.data.password);
          props.login({
            username: username,
            password: newPassword
          });
          setPasswordError(true);
          setShowChangePassword(false);
        } else {
          setError(res.data.error);
          setPasswordError(true);
        }
      })
      .catch(() => {
        setError("password change failed. Try again.");
      });
    console.log(error);
  };

  const handleshowChangePassword = e => {
    setShowChangePassword(true);
  };
  const handleVerifyPassword = () => {
    const newPass = newPassword.localeCompare(verifyNewPassword);
    if (newPass === 0) {
      console.log("new password matches!");
      setNewPasswordError(false);
    } else {
      console.log("new password does not match");
      setNewPasswordError(true);
    }
  };

  const handleClick = e => {
    setAnchorEl(e.currentTarget);
  };
  const handlePopperClose = () => {
    setAnchorEl(null);
  };

  const handleshowChangePasswordClose = () => {
    setShowChangePassword(false);
  };
  const HomeIcon = props => {
    return (
      <SvgIcon {...props}>
        <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" />
      </SvgIcon>
    );
  };
  React.useEffect(() => {
    if (userObj) {
      setUsername(userObj.username);
      setPassword(userObj.password);
    }
  }, [username, password, userObj]);

  const open = Boolean(anchorEl);
  const id = open ? "simple-popover" : undefined;
  const theme = useTheme();
  return (
    <div
      className="Home-root"
      style={{ backgroundColor: theme.palette.common.background }}
    >
      {!isUser || userObj === null ? <Redirect to="/login" /> : null}
      <div className="header-root">
        <AppBar
          position="static"
          style={{ backgroundColor: theme.palette.common.background }}
        >
          <Toolbar>
            <Typography
              variant="h6"
              className="header-title"
              style={{ color: theme.palette.common.messageName }}
            >
              {username}'s Profile
            </Typography>

            <Link to="/home">
              <HomeIcon
                fontSize="large"
                style={{ color: theme.palette.common.messageName }}
              />
            </Link>
          </Toolbar>
        </AppBar>
      </div>

      <div className="paper-root">
        <Paper
          style={{ backgroundColor: theme.palette.common.background }}
          elevation={5}
        >
          <Grid
            container
            spacing={2}
            direction="column"
            alignItems="center"
            justify="center"
          >
            <Grid item xs={4}>
              <Typography
                variant="h6"
                className="header-title"
                style={{ color: theme.palette.common.messageName }}
              >
                @{username}
              </Typography>
            </Grid>

            <Grid item xs={2}>
              <AccountCircleIcon color="primary" style={{ fontSize: 45 }} />
            </Grid>

            <Grid item xs={4}>
              <Button
                aria-describedby={id}
                variant="contained"
                color="primary"
                aria-haspopup="true"
                onClick={handleClick}
              >
                Edit Profile
              </Button>
              <Menu
                id="simple-menu"
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handlePopperClose}
              >
                <MenuItem
                  onClick={() => {
                    handleshowChangePassword();
                  }}
                >
                  Change Password
                </MenuItem>
              </Menu>
            </Grid>

            <Dialog
              open={showChangePassword}
              onClose={handleshowChangePasswordClose}
              aria-labelledby="form-dialog-title"
            >
              <DialogTitle id="form-dialog-title">Change Password</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  Please verify your old password before entering new password.
                </DialogContentText>

                <DialogContentText>
                  <strong>
                    {" "}
                    {passwordError ? "Incorrect password. Try again." : null}
                  </strong>
                </DialogContentText>

                <DialogContentText>
                  <strong>
                    {" "}
                    {newPasswordError
                      ? "Passwords does not match. Try Again"
                      : null}
                  </strong>
                </DialogContentText>

                <TextField
                  onChange={e => {
                    setOldPassword(e.target.value);
                  }}
                  error={passwordError}
                  autoFocus
                  margin="dense"
                  id="name"
                  label="Enter old Password"
                  type="password"
                  fullWidth
                />
                <TextField
                  onChange={e => {
                    setNewPassword(e.target.value);
                  }}
                  error={newPasswordError}
                  autoFocus
                  margin="dense"
                  id="name"
                  label="new Password"
                  type="password"
                  fullWidth
                />
                <TextField
                  onChange={e => {
                    setVerifyNewPassword(e.target.value);
                  }}
                  error={newPasswordError}
                  autoFocus
                  margin="dense"
                  id="name"
                  label="verify new Password"
                  type="password"
                  fullWidth
                />
              </DialogContent>
              <DialogActions>
                <Button onClick={handleshowChangePasswordClose} color="primary">
                  Cancel
                </Button>
                <Button onClick={handleChangePassword} color="primary">
                  Confirm
                </Button>
              </DialogActions>
            </Dialog>
          </Grid>
        </Paper>
      </div>
    </div>
  );
};
const mapStateToProps = state => {
  return { isUser: state.isUser, userObj: state.userObj };
};
function mapDispatchToProps(dispatch) {
  return {
    login: userObj => dispatch(login(userObj))
  };
}
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(User);
