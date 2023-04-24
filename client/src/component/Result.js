import styled from "styled-components"
import { useSelector } from "react-redux"

const CenterConponent = styled.div`
    background-color: #F1F2F3;
    width: 100%;
    height: calc(100vh - 50px);
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
`;

const Complete = styled.div`
    display: flex;
    padding: 20px;

    background-color: #DBF0E2;
    width: 550px;
    border-radius: 5px;
    border: 1px solid #A6D9B7;

    h3 {
        font-weight: 400;
        margin: 0;
        padding: 0;
    }

    p {
        margin: 10px 0 0 0;
        padding: 0;
        font-size: 10pt;
    }

    >div:first-child {
        width: 80px;
        text-align: center;
    }
`;

const Result = () => {
    const id = useSelector(state => state.id);

    return (
    <CenterConponent>
        <Complete>
            <div><svg aria-hidden="true" className="fc-success svg-icon iconCheckmarkLg" width="36" height="36" viewBox="0 0 36 36" fill="#3D8F58"><path d="m6 14 8 8L30 6v8L14 30l-8-8v-8Z"></path></svg></div>
            <div>
                <h3>Registration email sent to {id}. Open this email to finish sign up.</h3>
                <p>If you don't see this email in your inbox within 15 minutes, look for it in your junk mail folder. If you find it there, please mark the email as "Not Junk".</p>
            </div>
        </Complete>
    </CenterConponent>
)}

export default Result