
export default function Home ({props, navigate}){
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
                                <td id={c.isQueueOpen?"":"hide"}><input type="submit" onClick={e => navigate(c.id)} value="course room"/></td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    )
}