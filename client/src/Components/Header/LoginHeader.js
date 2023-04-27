import React from "react";
import styled from "styled-components";

const HeaderContainer = styled.header`
    width: 100%;
    display: flex;
    justify-content: center;
    background-color: #f8f9f9 ;
    height: 50px;
    position:fixed;
    z-index: 1;

    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
`;

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 50px;
    width: 100%;
    max-width: 1204px;
    padding: 0 20px;
    `;

const Logo = styled.div`
    img {
        height: 30px;
        padding: 10px;
        cursor: pointer;
        &:hover{
            background-color: #E3E6E8;
        }
    }
`;

const Nav = styled.nav`
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    font-size: 13px;
    line-height: 17px;
    a {
        text-align: left;
        color: #535961;

        
        text-decoration: none;
        &:hover{
            background-color: #E3E6E8;
            border-radius: 25px;
            height: 14px;
    }
}
`;

const Search = styled.div`
    width: 700px;
    height: 30px;
    border: 1px solid #ccc;
    position: relative;

    input {
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0 0 0 30px;
        border-radius: 3px;
        box-sizing: border-box;
        border: none;   
        &:focus {
            outline: none;
        }
    }

    svg {
        position: absolute;
        top: 7px;
        left: 5px;
    }
`;

const Auth = styled.div`
    width: 200px;
    display: flex;
    gap: 10px;

    svg {
        fill: #787878;
    }

    ul {
        width: 100%;
        height: 100%;
        list-style: none;
        display: flex;
        align-items: center;
        margin: 0;
        padding: 0;
        justify-content: space-between;
        

        li {
            display: flex;
            align-items: center;
            height: 100%;
            padding: 15px 10px;
            margin: 0;

            :first-child {            
            div {
                width: 25px;
                height: 25px;
                display: inline-block;
                background-color: red;
            }
            span {
                margin: 0 10px 0 5px;
                color: #787878;
                font-size: 9pt;
                font-weight: 700;
            }
            }

            :hover {
                background-color: #E3E6E8;

                svg {
                    fill: #232629;
                }

                span {
                    color: #000000;
                }
            }
        }
    }
`;

const Header = () => {
    return (
        <HeaderContainer>
            <Container>
                <Logo>
                    <img
                    src="logo/logo-stackoverflow_main.png"
                    alt="Stack Overflow Logo"
                    />
                </Logo>
                <Nav>
                    <a href="#">Products</a>
                </Nav>
                <Search>
                    <input type="text" placeholder="Search…" />
                    <svg aria-hidden="true" className="s-input-icon s-input-icon__search svg-icon iconSearch" fill="#787878" width="18" height="18" viewBox="0 0 18 18"><path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path></svg>
                </Search>
                <Auth>
                    <ul>
                        <li><div></div><span className="level">1</span></li>
                        <li><svg aria-hidden="true" className="svg-icon iconInbox" width="20" height="18" viewBox="0 0 20 18"><path d="M4.63 1h10.56a2 2 0 0 1 1.94 1.35L20 10.79V15a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-4.21l2.78-8.44c.25-.8 1-1.36 1.85-1.35Zm8.28 12 2-2h2.95l-2.44-7.32a1 1 0 0 0-.95-.68H5.35a1 1 0 0 0-.95.68L1.96 11h2.95l2 2h6Z"></path></svg></li>
                        <li><svg aria-hidden="true" className="svg-icon iconAchievements" width="18" height="18" viewBox="0 0 18 18"><path d="M15 2V1H3v1H0v4c0 1.6 1.4 3 3 3v1c.4 1.5 3 2.6 5 3v2H5s-1 1.5-1 2h10c0-.4-1-2-1-2h-3v-2c2-.4 4.6-1.5 5-3V9c1.6-.2 3-1.4 3-3V2h-3ZM3 7c-.5 0-1-.5-1-1V4h1v3Zm8.4 2.5L9 8 6.6 9.4l1-2.7L5 5h3l1-2.7L10 5h2.8l-2.3 1.8 1 2.7h-.1ZM16 6c0 .5-.5 1-1 1V4h1v2Z"></path></svg></li>
                        <li><svg aria-hidden="true" className="svg-icon iconHelp" width="18" height="18" viewBox="0 0 18 18"><path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8Zm.81 12.13c-.02.71-.55 1.15-1.24 1.13-.66-.02-1.17-.49-1.15-1.2.02-.72.56-1.18 1.22-1.16.7.03 1.2.51 1.17 1.23ZM11.77 8c-.59.66-1.78 1.09-2.05 1.97a4 4 0 0 0-.09.75c0 .05-.03.16-.18.16H7.88c-.16 0-.18-.1-.18-.15.06-1.35.66-2.2 1.83-2.88.39-.29.7-.75.7-1.24.01-1.24-1.64-1.82-2.35-.72-.21.33-.18.73-.18 1.1H5.75c0-1.97 1.03-3.26 3.03-3.26 1.75 0 3.47.87 3.47 2.83 0 .57-.2 1.05-.48 1.44Z"></path></svg></li>
                        <li><svg aria-hidden="true" className="svg-icon iconStackExchange" width="18" height="18" viewBox="0 0 18 18"><path d="M15 1H3a2 2 0 0 0-2 2v2h16V3a2 2 0 0 0-2-2ZM1 13c0 1.1.9 2 2 2h8v3l3-3h1a2 2 0 0 0 2-2v-2H1v2Zm16-7H1v4h16V6Z"></path></svg></li>
                    </ul>
                </Auth>
            </Container>
    </HeaderContainer>
    );
};

export default Header;
