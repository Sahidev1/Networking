
export default function Home ({props}){
    //console.log(JSON.stringify(props));
    return (
        <div className="home">  
            <p> Courselist:</p>
            <table>
                <tbody>
                    {props.map(c => {
                        return (
                            <tr key={c.id}>
                                <td> {c.title}</td>
                                <td> {c.isQueueOpen?"open":"locked"}</td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    )
}