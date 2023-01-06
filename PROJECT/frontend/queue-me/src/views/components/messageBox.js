
export default function MessageBox ({msgProp}){
    return (
        <div className="massagebox">
            <table>
                <tbody>
                    <tr>
                        <td>from: </td>
                        <td> {msgProp?.from} </td>
                    </tr>
                    <tr>
                        <td>time: </td>
                        <td> {msgProp?.time} </td>
                    </tr>
                    <tr><td>Message:</td></tr>
                </tbody>
            </table>
            <div className="messagetext"> {msgProp?.content} </div>
        </div>
    );
}