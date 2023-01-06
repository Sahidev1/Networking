
export default function MessageBox ({msgProp, closer}){
    return (
        <div className="messagebox">
            <div className="boxheader">MessageBox <input type="submit" value="X" onClick={e => closer()}/></div>
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