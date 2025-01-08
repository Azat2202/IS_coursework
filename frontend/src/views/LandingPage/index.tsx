import {Link} from "react-router-dom";
import {useAuth} from "react-oidc-context";

export function LandingPage() {
    const auth = useAuth()
    return (<>
        <h1>ИГРА БУНКЕР</h1>
        <h2>ИГРАЙТЕ ВСЕ</h2>
        <h3>это <b>ОЧЕНЬ</b> круто</h3>
        <h3>но сначлаа вам нужно <Link to={"/main"}>ЗАРЕГЕСТРИРОВАТЬСЯ У НАС</Link>
        </h3>
    </>)
}