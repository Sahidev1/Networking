
function getURL (apiPath){
    const BASE_PATH = process.env.REACT_APP_API_URL;
    return BASE_PATH + apiPath;
}

function getPostOptions (bodyObj){
    return (
        {
            method: 'POST',
            mode: 'cors',
            headers:{'Content-Type': 'application/json'},
            credentials: 'include',
            body: JSON.stringify(bodyObj)
        }
    );
}

export {getPostOptions, getURL};
