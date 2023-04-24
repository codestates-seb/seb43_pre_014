import React from "react";
import styled from "styled-components";
import {FaSearch} from "react-icons/fa";

const HeaderContainer = styled.header`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f8f9f9 ;
    height: 50px;
    `;

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 50px;
    width: 100%;
    max-width: 1200px;
    padding: 0 20px;
    `;

const Logo = styled.div`
    img {
        height: 30px;
        padding: 10px;
        cursor: pointer;
        &: hover{
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
        &: hover{
            background-color: #E3E6E8;
            border-radius: 25px;
            height: 14px;
    }
    `;

const Search = styled.div`
    width: 550px;
    height: 30px;
    border: 1px solid #ccc;
    }

    input {
        width: 500px;
        height: 10px;
        padding: 7px;
        margin-left: 5px;
        border-radius: 3px;
        border: none;   
        &:focus {
            outline: none;
        }
    }
`;

const Auth = styled.div`
    display: flex;
    gap: 10px;
    `;
    
    const LoginButton = styled.button`
    width: 59px;
    height: 33px;
    border-width: 1px;
    border-radius: 3px;
    box-shadow: #ffffff 0px 1px 0px 0px;
    border: none;
    background-color: #e1ecf4;
    color: #0995ff;
    border: solid 1px #0995ff;
    padding: 8px 10.4px;
    font-size: 13px;
    text-align: center;
    line-height: 15px;

    cursor: pointer;
    `;

const SignupButton = styled.button`
    width: 68   px;
    height: 33px;
    border-radius: 3px;
    box-shadow: #ffffff 0px 1px 0px 0px;
    border: none;
    background-color: #0995ff;
    padding: 8px 10.4px;
    color: #e1ecf4;
    font-size: 13px;
    cursor: pointer;
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
                    <a href="#">About</a>
                    <a href="#">Products</a>
                    <a href="#">For Teams</a>
                </Nav>
                <Search>
                    <FaSearch /> 
                    <input 
                    type="text" placeholder="Search…" />
                </Search>
                <Auth>
                    <LoginButton>Log in</LoginButton>
                    <SignupButton>Sign up</SignupButton>
                </Auth>
            </Container>
    </HeaderContainer>
    );
};

export default Header;
