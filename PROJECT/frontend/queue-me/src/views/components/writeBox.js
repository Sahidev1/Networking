
export default function WriteBox ({sendProps, unset, msgRef, sender}){
    return (
        <div className="writebox"> 
            <p><label>Write message to {sendProps?.username} </label></p>
            <textarea ref={msgRef} rows="5" cols="50"/>
            <p>
                <input type="submit" value="send" onClick={e => sender(sendProps?.user_id)} />
                <input type="submit" value="cancel" onClick={e => unset()}/>
            </p>
        </div>
    );
}