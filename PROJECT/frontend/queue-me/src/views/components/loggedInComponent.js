
export default function LoggedInComponent ({username, role}){
    return (
        <div className="logcomp">
            <table>
                <tbody>
                    <tr>
                        <td>Username: {username}</td>
                        <td>Role: {role} </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}