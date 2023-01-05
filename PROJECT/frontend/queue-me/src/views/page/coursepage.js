
export default function CoursePage ({props, user, dequeue, refArr, clickAdd}){
    return (
        <div className="coursepage">
            <p> Course Queue </p>
            <table className="queuetable">
                <thead>
                    <tr>
                        <th>username</th>
                        <th>location</th>
                        <th>comment</th>
                    </tr>
                </thead>
                <tbody>
                    {props?.map(item => {
                        return (
                            <tr id={(user?.id == item.user_id)?"green":""} key={item.id}>
                                <td>{item.username}</td>
                                <td>{item.location}</td>
                                <td>{item.comment}</td>
                                <td id={((user?.id == item.user_id)|| user.admin)?"":"hide"}>
                                    <input type="submit" value="dequeue" onClick={e => dequeue(item.id)}/>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            <div>
                {refArr?.map((reference, index) => {
                    return (
                        <p key={index}>{index === 0?"location ":"comment "}<input type="text" ref={reference}/></p>
                    );
                })}
                <p><input type="submit" value="join queue" onClick={e => clickAdd()} /></p>
            </div>
        </div>
    );
}