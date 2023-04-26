import { useDispatch } from "react-redux";
import { logout } from "../store/userSlice";

const LogOutBtn = () => {
    const dispatch = useDispatch();

    const handleLogout = () => {
        dispatch(logout());
        localStorage.removeItem("jwt");
    };

    return (
        <button onClick={handleLogout}>Logout</button>
    );
};

export default LogOutBtn;