import { LOGIN, LOGOUT, SETUSERS} from "../constants";

export function login(userObj) {
  return { type: LOGIN, userObj };
}

export function logout() {
  return { type: LOGOUT };
}

export function setUsers(usersList) {
  return { type: SETUSERS, usersList };
}