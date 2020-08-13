import { LOGIN, LOGOUT, SETUSERS} from "../constants";

const initialState = {
  userObj: null,
  isUser: false,
  usersList: null,
};

function rootReducer(state = initialState, action) {
  if (action.type === LOGIN) {
    return Object.assign({}, state, {
      userObj: action.userObj,
      isUser: true
    });
  } else if (action.type === LOGOUT) {
    return Object.assign({}, state, {
      userObj: null,
      isUser: false
    });
  } else if (action.type === SETUSERS) {
    return Object.assign({}, state, {
      usersList: action.usersList
    });
  }
  return state;
}

export default rootReducer;
