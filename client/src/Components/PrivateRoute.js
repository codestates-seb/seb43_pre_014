import { useSelector } from "react-redux";
import { Outlet, Navigate, useLocation } from "react-router-dom";

const PrivateRoute = ({ path, element }) => {
    const isAuthenticated = useSelector((state) => state.user.isAuthenticated);
    const location = useLocation();

    return isAuthenticated ? <Outlet /> : <Navigate to="/login" state={{ from: location }} />;
};

export default PrivateRoute;