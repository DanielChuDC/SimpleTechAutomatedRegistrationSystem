package cx2002grp2.stars.data.database;

import cx2002grp2.stars.data.converter.CourseIndexConverter;
import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.util.SimpleMapLoader;

public class CourseIndexDB extends SingleStringKeyDatabase<CourseIndex> {
    
    private static final String DB_FILE_PATH = "tables/course_index.csv";

    @Override
    protected void loadData() {
        CourseIndexConverter converter = new CourseIndexConverter();
        SimpleMapLoader<CourseIndex> loader = new SimpleMapLoader<CourseIndex>();

        loader.load(DB_FILE_PATH, this.getDataMap(), converter);
    }

    @Override
    protected void saveData() {
        CourseIndexConverter converter = new CourseIndexConverter();
        SimpleMapLoader<CourseIndex> loader = new SimpleMapLoader<CourseIndex>();

        loader.save(this.getDataMap(), DB_FILE_PATH, converter);
    }
    
}
