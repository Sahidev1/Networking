
export default function CoursePage ({props, user}){
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
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
}