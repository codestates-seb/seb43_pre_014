import styled from "styled-components"


// 반응형으로 특정 창 이상 줄이게 되면 없어지게끔 구현해야 함.
const Menu = styled.div`
width: 185px;
border-right: 1px solid lightgray;
font-size: 10pt;

>ul {
    list-style: none;
    padding: 0;
    margin: 0;

    >li {            
        margin: 20px;
        width: calc(100% - 20px);
        
        >ul {
        list-style: none;
        padding: 0;

        >li:first-child {
            margin-top: 5px;
            font-weight: 500;
            color: black;
            background-color: #F1F2F3;
            border-right: 3px solid #F48225;
        }

        li {
            padding: 7px 0 7px 20px;
        }
        }
    }
}

a:link, a:visited {
    color: black;
    text-decoration: none;
}

@media only screen and (max-width: 1024px) {
    display: none;
}
`;

const Sidemenu = ({questions}) => { 
return (
<>
    <Menu>
        <ul>
            <li><a href="/">Home</a></li>
            <li>
                <ul>PUBLIC
                    <li><a href="/">Questions</a></li>
                    <li>Users</li>
                </ul>
            </li>
        </ul>
    </Menu>
</>
);
}

export default Sidemenu