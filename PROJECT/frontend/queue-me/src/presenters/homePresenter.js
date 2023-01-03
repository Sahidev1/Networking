import { useState } from "react"
import { checkLocalForAuth } from "../util/helpers"

export default function homePresenter (){
    const [user, setUser] = useState(checkLocalForAuth());

    return (
        <div>
        </div>);
}