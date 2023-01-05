
export default function Home ({toggle, props, navigate, isAdmin}){
    //console.log(JSON.stringify(props));
    return (
        <div className="home">  
            <p> Courselist:</p>
            <table className="coursetable">
                <tbody>
                    {props.map(c => {
                        return (
                            <tr key={c.id}>
                                <td> {c.title}</td>
                                <td> {c.isQueueOpen?"open":"locked"}</td>
                                <td ><input id={c.isQueueOpen?"":"hide"} type="submit" onClick={e => navigate(c.id)} value="course room"/></td>
                                <td id={isAdmin?"":"hide"}>
                                    <input type="submit" value={c.isQueueOpen?"lock":"unlock"} onClick={e => toggle(c.id)} />
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    )
}