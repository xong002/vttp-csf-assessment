export class News {
    title! : string;
    description!: string;
    tags!: string[] 
}

export class Tag {
    tag!: string;
    count!: number;
}

export class NewsDetails {
    title! : string;
    description! : string;
    postDate!: number;
    image!: string;
    tags!: string[];
}