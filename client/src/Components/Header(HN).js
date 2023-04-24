import styled from "styled-components"

const HeaderComponent = styled.div`
    background-color: #F1F2F3;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 50px;
    overflow: hidden;
    position: fixed;
    z-index: 1;
`;

const Header = () => {
    return (
        <HeaderComponent>
            About Products For Teams Search…
        </HeaderComponent>
)}

export default Header