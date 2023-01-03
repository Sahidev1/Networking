import { useEffect, useState } from "react"
import { getGetOptions, getURL } from "../util/apihelpers";
import Home from "../views/page/home";

export default function HomePresenter (){
    const [courses, setCourses] = useState (null);
    const getCourses = async () => {
        const retURL = getURL('courses/');
        const options = getGetOptions();

        try {
            const resp = await fetch (retURL, options);
            const data = await resp.json();
            if (data.status === "success") return data;
        } catch (err){
            console.log(err);
        }
        return null;
    }
    console.log("mango: " + JSON.stringify(courses));
    useEffect(() => {
        const courseHandler = async () => {
            const result = await getCourses();
            //console.log(result);
            if (result){
                console.log(result);
                setCourses(result);
            }
            //console.log("ran");
        }
        courseHandler();
    },[])

    return (courses?<Home props={courses.courselist}/>:<div>Loading!</div>);
}